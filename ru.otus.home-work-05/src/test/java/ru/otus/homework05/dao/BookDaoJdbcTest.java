package ru.otus.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({BookDaoJdbc.class, GenreDaoJdbc.class, AuthorDaoJdbc.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса BookDaoJdbc")
class BookDaoJdbcTest {

    private static final int FIND_ALL_EXPECTED_RESULT_COUNT = 2;
    private static final Long NEW_BOOK3_ID = 3L;
    private static final String NEW_BOOK3_NAME = "New book3";
    private static final Long NEW_BOOK4_ID = 4L;
    private static final String NEW_BOOK4_NAME = "New book4";
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

    @Autowired
    private BookDao dao;
    @Autowired
    private NamedParameterJdbcOperations jdbc;

    @Test
    @DisplayName("Проверяем сохранение одной записи")
    @Rollback
    void save() {

        var unexpectedBook = dao.findById(NEW_BOOK3_ID);
        assertTrue(unexpectedBook.isEmpty());

        var expectedBook = new Book()
                .setId(NEW_BOOK3_ID)
                .setName(NEW_BOOK3_NAME)
                .setGenre(getGenreForTest())
                .setAuthors(getAuthorsListForTest());

        var newBook = new Book()
                .setName(NEW_BOOK3_NAME)
                .setGenre(getGenreForTest())
                .setAuthors(getAuthorsListForTest());

        assertEquals(expectedBook, dao.save(newBook));
        rollbackSequence(3);
    }


    @Test
    @DisplayName("Проверяем сохранение коллекции записей")
    @Rollback
    void saveAll() {
        var unexpectedBook3 = dao.findById(NEW_BOOK3_ID);
        assertTrue(unexpectedBook3.isEmpty());
        var unexpectedBook4 = dao.findById(NEW_BOOK4_ID);
        assertTrue(unexpectedBook4.isEmpty());

        var expectedBooks = List.of(
                new Book()
                        .setId(NEW_BOOK3_ID)
                        .setName(NEW_BOOK3_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest()),
                new Book()
                        .setId(NEW_BOOK4_ID)
                        .setName(NEW_BOOK4_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest())
        );

        var newBooks = List.of(
                new Book()
                        .setName(NEW_BOOK3_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest()),
                new Book()
                        .setName(NEW_BOOK4_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest())
        );

        assertThat(dao.saveAll(newBooks))
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedBooks);
        rollbackSequence(3);
    }

    @Test
    @DisplayName("Проверяем поиск по ID")
    void findById() {
        var expectedBook = Optional.ofNullable(
                new Book()
                        .setId(FIRST_BOOK_ID)
                        .setName(FIRST_BOOK_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest())
        );

        assertEquals(expectedBook, dao.findById(FIRST_BOOK_ID));
    }

    @Test
    @DisplayName("Проверяем существование по ID")
    void existsById() {
        assertTrue(dao.existsById(FIRST_BOOK_ID));
    }

    @Test
    @DisplayName("Проверяем поиск всех элементов")
    void findAll() {
        var expectedBooks = List.of(
                new Book()
                        .setId(FIRST_BOOK_ID)
                        .setName(FIRST_BOOK_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest()),
                new Book()
                        .setId(SECOND_BOOK_ID)
                        .setName(SECOND_BOOK_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(List.of())
        );

        assertThat(dao.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedBooks);
    }

    @Test
    @DisplayName("Проверяем поиск по коллекции ID")
    void findAllById() {
        var expectedBooks = List.of(
                new Book()
                        .setId(FIRST_BOOK_ID)
                        .setName(FIRST_BOOK_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest()),
                new Book()
                        .setId(SECOND_BOOK_ID)
                        .setName(SECOND_BOOK_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(List.of())
        );
        var ids = List.of(FIRST_BOOK_ID, SECOND_BOOK_ID);

        assertThat(dao.findAllById(ids))
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedBooks);
    }

    @Test
    @DisplayName("Проверяем возврат количесва элементов в таблице")
    void count() {
        assertEquals(FIND_ALL_EXPECTED_RESULT_COUNT, dao.count());
    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление по ID")
    void deleteById() {
        dao.deleteById(FIRST_BOOK_ID);
        assertFalse(dao.existsById(FIRST_BOOK_ID));
    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление одной записи")
    void delete() {
        var bookToDelete = new Book()
                .setId(FIRST_BOOK_ID)
                .setName(FIRST_BOOK_NAME)
                .setGenre(getGenreForTest())
                .setAuthors(getAuthorsListForTest());

        assertTrue(dao.existsById(FIRST_BOOK_ID));
        dao.delete(bookToDelete);
        assertFalse(dao.existsById(FIRST_BOOK_ID));

    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление по коллекции ID")
    void deleteAllById() {
        var ids = List.of(FIRST_BOOK_ID, SECOND_BOOK_ID);
        assertTrue(dao.existsById(FIRST_BOOK_ID));
        assertTrue(dao.existsById(SECOND_BOOK_ID));

        dao.deleteAllById(ids);

        assertFalse(dao.existsById(FIRST_BOOK_ID));
        assertFalse(dao.existsById(SECOND_BOOK_ID));
    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление всех записей")
    void deleteAll() {
        assertNotEquals(0, dao.count());

        dao.deleteAll();

        assertEquals(0, dao.count());
    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление коллекции записей")
    void testDeleteAll() {
        var booksForDelete = List.of(
                new Book()
                        .setId(FIRST_BOOK_ID)
                        .setName(FIRST_BOOK_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(getAuthorsListForTest()),
                new Book()
                        .setId(SECOND_BOOK_ID)
                        .setName(SECOND_BOOK_NAME)
                        .setGenre(getGenreForTest())
                        .setAuthors(List.of())
        );

        assertTrue(dao.existsById(FIRST_BOOK_ID));
        assertTrue(dao.existsById(SECOND_BOOK_ID));

        dao.deleteAll(booksForDelete);

        assertFalse(dao.existsById(FIRST_BOOK_ID));
        assertFalse(dao.existsById(SECOND_BOOK_ID));
    }

    private void rollbackSequence(int n) {
        jdbc.update(
                "alter sequence public.book_sequence_id restart with :n",
                Map.of("n", n)
        );
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