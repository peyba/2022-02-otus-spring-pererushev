package ru.otus.homework05.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework05.Application;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Genre;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private LibraryService library;

    @Test
    @DisplayName("Проверяем сохранение книги")
    void saveBook() {
        var newBook = new Book()
                .setName(NEW_BOOK3_NAME)
                .setGenre(getGenreForTest())
                .setAuthors(getAuthorsListForTest());

        assertNotNull(library.saveBook(newBook).getId());
        library.deleteBookById(library.saveBook(newBook).getId());
    }

    @Test
    @DisplayName("Проверяем получение всех книг")
    void getAllBooks() {
        assertEquals(FIND_ALL_EXPECTED_RESULT_COUNT, library.getAllBooks().size());
    }

    @Test
    @DisplayName("Проверяем поиск книги по ID")
    void findBookById() {
        var expectedBook = Optional.ofNullable(
                new Book()
                        .setId(FIRST_BOOK_ID)
                        .setName(FIRST_BOOK_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest())
        );

        assertEquals(expectedBook, library.findBookById(FIRST_BOOK_ID));
    }

    @Test
    @DisplayName("Проверяем удаление книги")
    void deleteBookById() {
        assertTrue(library.findBookById(FIRST_BOOK_ID).isPresent());

        library.deleteBookById(FIRST_BOOK_ID);

        assertTrue(library.findBookById(FIRST_BOOK_ID).isEmpty());
    }

    @Test
    @DisplayName("Проверяем получение всех жанров")
    void getAllGenres() {
        assertEquals(GENRES_COUNT, library.getAllGenres().size());
    }

    @Test
    @DisplayName("Проверяем поиск жанра по ID")
    void findGenreById() {
        var expectedGenre = Optional.ofNullable(getGenreForTest());

        assertEquals(expectedGenre, library.findGenreById(GENRE_ID));
    }

    @Test
    @DisplayName("Проверяем получение всех авторов")
    void getAllAuthors() {
        assertEquals(AUTHORS_COUNT, library.getAllAuthors().size());
    }

    @Test
    @DisplayName("Проверяем поиск автора по ID")
    void findAuthorById() {
        var expectedAuthor = Optional.ofNullable(
                new Author()
                        .setId(FIRST_AUTHOR_ID)
                        .setFirstName(FIRST_AUTHOR_NAME)
                        .setSecondName(FIRST_AUTHOR_NAME)
        );

        assertEquals(expectedAuthor, library.findAuthorById(FIRST_AUTHOR_ID));
    }

    private List<Author> getAuthorsListForTest() {
        return List.of(
                new Author().setId(FIRST_AUTHOR_ID).setFirstName(FIRST_AUTHOR_NAME).setSecondName(FIRST_AUTHOR_NAME),
                new Author().setId(SECOND_AUTHOR_ID).setFirstName(SECOND_AUTHOR_NAME).setSecondName(SECOND_AUTHOR_NAME)
        );
    }

    private Genre getGenreForTest() {
        return new Genre()
                .setId(GENRE_ID)
                .setCode(GENRE_CODE);
    }
}