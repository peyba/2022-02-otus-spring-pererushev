package ru.otus.homework09.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework09.dto.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@DisplayName("Тестирование класса BookLibraryServiceImpl")
@Transactional
class BookLibraryServiceImplTest {

    private final GenreDto crime = new GenreDto().setId(1L).setCode("CRIME");
    private final GenreDto detective = new GenreDto().setId(2L).setCode("DETECTIVE");
    private final AuthorDto author1 = new AuthorDto().setId(1L).setFirstName("Author1").setSecondName("Author1");
    private final AuthorDto author2 = new AuthorDto().setId(2L).setFirstName("Author2").setSecondName("Author2");
    private final BookCommentDto comment1 = new BookCommentDto().setId(1L).setText("Good, but shor");
    private final BookCommentDto comment2 = new BookCommentDto().setId(2L).setText("Out of stock");
    private final BookCommentDto comment3 = new BookCommentDto().setId(3L).setText("Out of stock");
    private final BookCommentDto comment4 = new BookCommentDto().setId(4L).setText("Very boooooriiiiing!!!");
    private final BookCommentDto comment5 = new BookCommentDto().setId(5L).setText("Got it.");
    private final BookDto book1 = new BookDto().setId(1L).setName("book1").setGenre(crime).setAuthors(Set.of(author1, author2))
            .setBookComments(Set.of(comment1, comment2));
    private final BookDto book2 = new BookDto().setId(2L).setName("book2").setGenre(detective)
            .setBookComments(Set.of(comment3, comment4, comment5));
    private final BookTitleDto bookTitle1 = new BookTitleDto().setId(1L).setName("book1").setGenre(crime).setAuthors(Set.of(author1, author2));
    private final BookTitleDto bookTitle2 = new BookTitleDto().setId(2L).setName("book2").setGenre(detective).setAuthors(Set.of());

    @Autowired
    private BookLibraryService bookLibraryService;

    @Test
    @DisplayName("Проверка сохранения новой книги")
    void save() {
    }

    @Test
    @DisplayName("Проверка получения всех элементов")
    void getAll() {
        List<BookTitleDto> expectedBooks = List.of(bookTitle1, bookTitle2);

        var all = bookLibraryService.getAll();

        assertArrayEquals(expectedBooks.toArray(), all.toArray());
    }

    @Test
    @DisplayName("Проверка получения элемента по ID")
    void findById() {
        var expectedBook = Optional.of(book1);

        var foundBook = bookLibraryService.findById(book1.getId());

        assertEquals(expectedBook, foundBook);
    }

    @Test
    @DisplayName("Проверка удаления книги по ID")
    void deleteById() {
        assertNotEquals(Optional.empty(), bookLibraryService.findById(book1.getId()));

        bookLibraryService.deleteById(book1.getId());

        assertEquals(Optional.empty(), bookLibraryService.findById(book1.getId()));
    }

    @Test
    @DisplayName("Проверка получения всех комментариев к книге")
    void getAllComments() {
        Set<BookCommentDto> expectedComments = book2.getBookComments();

        var book2CommentsComments = bookLibraryService.getAllComments(book2.getId());

        assertEquals(expectedComments, book2CommentsComments);
    }

    @Test
    @DisplayName("Проверка получения комментария по ID")
    void findCommentById() {
        var expectedComment = Optional.of(comment2);

        var foundComment = bookLibraryService.findCommentById(comment2.getId());

        assertEquals(expectedComment, foundComment);
    }

    @Test
    @DisplayName("Проверка удаления комментария по ID")
    void deleteCommentById() {
        assertNotEquals(Optional.empty(), bookLibraryService.findCommentById(comment2.getId()));
        Assertions.assertEquals(2, bookLibraryService.findById(book1.getId()).get().getBookComments().size());

        bookLibraryService.deleteCommentById(comment2.getId());

        assertEquals(Optional.empty(), bookLibraryService.findCommentById(comment2.getId()));
        Assertions.assertEquals(1, bookLibraryService.findById(book1.getId()).get().getBookComments().size());
    }
}