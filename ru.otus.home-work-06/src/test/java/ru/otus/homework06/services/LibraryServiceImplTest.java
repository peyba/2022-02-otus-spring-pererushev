package ru.otus.homework06.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.dto.AuthorDto;
import ru.otus.homework06.dto.BookCommentDto;
import ru.otus.homework06.dto.BookDto;
import ru.otus.homework06.dto.GenreDto;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@DisplayName("Тестирование класса LibraryServiceImp")
class LibraryServiceImplTest {

    private static final int FIND_ALL_EXPECTED_RESULT_COUNT = 2;
    private static final Long NEW_BOOK3_ID = 3L;
    private static final String NEW_BOOK3_NAME = "New book3";
    private static final Long NEW_BOOK4_ID = 4L;
    private static final String NEW_BOOK4_NAME = "New book4";
    private static final int GENRES_COUNT = 2;
    private static final Long GENRE_ID = 1L;
    private static final String GENRE_CODE = "CRIME";
    private static final Long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author1";
    private static final Long SECOND_AUTHOR_ID = 2L;
    private static final String SECOND_AUTHOR_NAME = "Author2";
    private static final Long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_NAME = "book1";
    private static final Long SECOND_BOOK_ID = 2L;
    private static final String SECOND_BOOK_NAME = "book2";
    private static final int AUTHORS_COUNT = 2;
    private static final Long FIRST_BOOK_COMMENT1_ID = 1L;
    private static final String FIRST_BOOK_COMMENT1 = "Good, but shor";
    private static final Long FIRST_BOOK_COMMENT2_ID = 2L;
    private static final String FIRST_BOOK_COMMENT2 = "Out of stock";
    private static final Long SECOND_BOOK_COMMENT1_ID = 3L;
    private static final String SECOND_BOOK_COMMENT1 = "Out of stock";
    private static final Long SECOND_BOOK_COMMENT2_ID = 4L;
    private static final String SECOND_BOOK_COMMENT2 = "Very boooooriiiiing!!!";
    private static final Long SECOND_BOOK_COMMENT3_ID = 5L;
    private static final String SECOND_BOOK_COMMENT3 = "Got it.";

    @Autowired
    private LibraryBookService libraryBookService;
    @Autowired
    private LibraryAuthorService libraryAuthorService;
    @Autowired
    private LibraryGenreService libraryGenreService;

    @Test
    @DisplayName("Проверяем сохранение книги")
    @Transactional
    @Rollback
    void saveBook() {
        var newBook = new BookDto()
                .setName(NEW_BOOK3_NAME)
                .setGenre(getGenreForTest())
                .setAuthors(getAuthorsListForTest());

        assertNotNull(libraryBookService.save(newBook).getId());

        libraryBookService.deleteById(libraryBookService.save(newBook).getId());
    }

    @Test
    @Transactional
    @DisplayName("Проверяем получение всех книг")
    void getAllBooks() {
        assertEquals(FIND_ALL_EXPECTED_RESULT_COUNT, libraryBookService.getAll().size());
    }

    @Test
    @Transactional
    @DisplayName("Проверяем поиск книги по ID")
    void findById() {
        var expectedBook = Optional.ofNullable(
                new BookDto()
                        .setId(FIRST_BOOK_ID)
                        .setName(FIRST_BOOK_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest())
                        .setBookComments(getCommentsForBook(FIRST_BOOK_ID))
        );

        var book = libraryBookService.findById(FIRST_BOOK_ID);
        assertEquals(expectedBook, book);
    }

    @Test
    @DisplayName("Проверяем удаление книги")
    @Rollback
    void deleteById() {
        assertTrue(libraryBookService.findById(FIRST_BOOK_ID).isPresent());

        libraryBookService.deleteById(FIRST_BOOK_ID);

        assertTrue(libraryBookService.findById(FIRST_BOOK_ID).isEmpty());
    }

    @Test
    @DisplayName("Проверяем получение всех жанров")
    void getAllGenres() {
        assertEquals(GENRES_COUNT, libraryGenreService.getAll().size());
    }

    @Test
    @DisplayName("Проверяем поиск жанра по ID")
    void findGenreById() {
        var expectedGenre = Optional.ofNullable(getGenreForTest());

        assertEquals(expectedGenre, libraryGenreService.findById(GENRE_ID));
    }

    @Test
    @DisplayName("Проверяем получение всех авторов")
    void getAllAuthors() {
        assertEquals(AUTHORS_COUNT, libraryAuthorService.getAll().size());
    }

    @Test
    @DisplayName("Проверяем поиск автора по ID")
    void findAuthorById() {
        var expectedAuthor = Optional.ofNullable(
                new AuthorDto()
                        .setId(FIRST_AUTHOR_ID)
                        .setFirstName(FIRST_AUTHOR_NAME)
                        .setSecondName(FIRST_AUTHOR_NAME)
        );

        assertEquals(expectedAuthor, libraryAuthorService.findById(FIRST_AUTHOR_ID));
    }

    private Set<AuthorDto> getAuthorsListForTest() {
        return Set.of(
                new AuthorDto().setId(FIRST_AUTHOR_ID).setFirstName(FIRST_AUTHOR_NAME).setSecondName(FIRST_AUTHOR_NAME),
                new AuthorDto().setId(SECOND_AUTHOR_ID).setFirstName(SECOND_AUTHOR_NAME).setSecondName(SECOND_AUTHOR_NAME)
        );
    }

    private GenreDto getGenreForTest() {
        return new GenreDto()
                .setId(GENRE_ID)
                .setCode(GENRE_CODE);
    }

    private Set<BookCommentDto> getCommentsForBook(Long bookId) {
        if (bookId.equals(FIRST_BOOK_ID)) {
            return Set.of(
                    new BookCommentDto().setId(FIRST_BOOK_COMMENT1_ID).setText(FIRST_BOOK_COMMENT1),
                    new BookCommentDto().setId(FIRST_BOOK_COMMENT2_ID).setText(FIRST_BOOK_COMMENT2)
            );
        } else if (bookId.equals(SECOND_BOOK_ID)) {
            return Set.of(
                    new BookCommentDto().setId(SECOND_BOOK_COMMENT1_ID).setText(SECOND_BOOK_COMMENT1),
                    new BookCommentDto().setId(SECOND_BOOK_COMMENT2_ID).setText(SECOND_BOOK_COMMENT2),
                    new BookCommentDto().setId(SECOND_BOOK_COMMENT3_ID).setText(SECOND_BOOK_COMMENT3)
            );
        } else {
            return Set.of();
        }
    }
}