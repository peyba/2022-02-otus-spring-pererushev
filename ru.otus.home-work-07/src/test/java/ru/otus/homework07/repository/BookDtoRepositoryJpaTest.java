package ru.otus.homework07.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework07.domain.Author;
import ru.otus.homework07.domain.Book;
import ru.otus.homework07.domain.BookComment;
import ru.otus.homework07.domain.Genre;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BookRepositoryJpa.class, GenreRepositoryJpa.class, AuthorRepositoryJpa.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса BookDaoJdbc")
class BookDtoRepositoryJpaTest {

    private static final int FIND_ALL_EXPECTED_RESULT_COUNT = 2;
    private static final Long NEW_BOOK3_ID = 3L;
    private static final String NEW_BOOK3_NAME = "New book3";
    private static final Long NEW_BOOK4_ID = 4L;
    private static final String NEW_BOOK4_NAME = "New book4";
    private static final Long CRIME_GENRE_ID = 1L;
    private static final String CRIME_GENRE_CODE = "CRIME";
    private static final Long DETECTIVE_GENRE_ID = 2L;
    private static final String DETECTIVE_GENRE_CODE = "DETECTIVE";
    private static final Long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author1";
    private static final Long SECOND_AUTHOR_ID = 2L;
    private static final String SECOND_AUTHOR_NAME = "Author2";
    private static final Long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_NAME = "book1";
    private static final Long SECOND_BOOK_ID = 2L;
    private static final String SECOND_BOOK_NAME = "book2";
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
    private static final Long START_BOOK_SEQUENCE = 3L;
    private static final Long START_BOOK_COMMENT_SEQUENCE = 6L;

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Проверяем сохранение записеи")
    @Rollback
    void save() {
        assertNull(em.find(Book.class, NEW_BOOK3_ID));

        var expectedBook = Optional.ofNullable(
                new Book()
                        .setId(NEW_BOOK3_ID)
                        .setName(NEW_BOOK3_NAME)
                        .setGenre(getCrimeGenreForTest())
                        .setAuthors(getAuthorsListForTest())
                        .setBookComments(getCommentsForBook(NEW_BOOK3_ID))
        );

        var newBook = new Book()
                .setName(NEW_BOOK3_NAME)
                .setGenre(getCrimeGenreForTest())
                .setAuthors(getAuthorsListForTest());
        newBook
                .setBookComments(Set.of(
                        new BookComment().setBook(newBook).setText(FIRST_BOOK_COMMENT1)
                ));

        repository.save(newBook);
        em.flush();
        em.clear();

        assertEquals(expectedBook, repository.findById(NEW_BOOK3_ID));

        setNextBookSequence(START_BOOK_SEQUENCE);
        setNextBookCommentSequence(START_BOOK_COMMENT_SEQUENCE);
    }

    @Test
    @DisplayName("Проверяем сохранение коллекции записей")
    @Rollback
    void saveAll() {
        assertNull(em.find(Book.class, NEW_BOOK3_ID));
        assertNull(em.find(Book.class, NEW_BOOK4_ID));

        var expectedBooks = List.of(
                new Book()
                        .setId(NEW_BOOK3_ID)
                        .setName(NEW_BOOK3_NAME)
                        .setGenre(getCrimeGenreForTest())
                        .setAuthors(getAuthorsListForTest())
                        .setBookComments(Set.of()),
                new Book()
                        .setId(NEW_BOOK4_ID)
                        .setName(NEW_BOOK4_NAME)
                        .setGenre(getCrimeGenreForTest())
                        .setAuthors(getAuthorsListForTest())
                        .setBookComments(Set.of())
        );

        var newBooks = List.of(
                new Book()
                        .setName(NEW_BOOK3_NAME)
                        .setGenre(getCrimeGenreForTest())
                        .setAuthors(getAuthorsListForTest())
                        .setBookComments(Set.of()),
                new Book()
                        .setName(NEW_BOOK4_NAME)
                        .setGenre(getCrimeGenreForTest())
                        .setAuthors(getAuthorsListForTest())
                        .setBookComments(Set.of())
        );

        repository.saveAll(newBooks);
        em.flush();
        em.clear();

        var savedBooks = new ArrayList<>();
        savedBooks.add(em.find(Book.class, NEW_BOOK3_ID));
        savedBooks.add(em.find(Book.class, NEW_BOOK4_ID));

        assertThat(savedBooks)
                .usingDefaultElementComparator()
                .containsAll(expectedBooks);

        setNextBookSequence(START_BOOK_SEQUENCE);
        setNextBookCommentSequence(START_BOOK_COMMENT_SEQUENCE);
    }

    @Test
    @DisplayName("Проверяем существование по ID")
    void existsById() {
        assertTrue(repository.existsById(FIRST_BOOK_ID));
    }

    @Test
    @DisplayName("Проверка поиска элемента по ID")
    void findById() {
        var expectedBook = Optional.of(
                new Book()
                        .setId(FIRST_BOOK_ID)
                        .setName(FIRST_BOOK_NAME)
                        .setGenre(getCrimeGenreForTest())
                        .setAuthors(getAuthorsListForTest())
                        .setBookComments(getCommentsForBook(FIRST_BOOK_ID))
        );

        var foundedBook = repository.findById(FIRST_BOOK_ID);
        assertEquals(expectedBook, foundedBook);
    }

    @Test
    @DisplayName("Проверяем поиск всех элементов")
    void findAll() {
        var expectedBooks = List.of(
                new Book()
                        .setId(FIRST_BOOK_ID)
                        .setName(FIRST_BOOK_NAME)
                        .setGenre(getCrimeGenreForTest())
                        .setAuthors(getAuthorsListForTest())
                        .setBookComments(getCommentsForBook(FIRST_BOOK_ID)),
                new Book()
                        .setId(SECOND_BOOK_ID)
                        .setName(SECOND_BOOK_NAME)
                        .setGenre(getDetectiveGenreForTest())
                        .setAuthors(Set.of())
                        .setBookComments(getCommentsForBook(SECOND_BOOK_ID))
        );
        var book = repository.findAll();
        assertThat(book)
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
                        .setGenre(getCrimeGenreForTest())
                        .setAuthors(getAuthorsListForTest())
                        .setBookComments(getCommentsForBook(FIRST_BOOK_ID)),
                new Book()
                        .setId(SECOND_BOOK_ID)
                        .setName(SECOND_BOOK_NAME)
                        .setGenre(getDetectiveGenreForTest())
                        .setAuthors(Set.of())
                        .setBookComments(getCommentsForBook(SECOND_BOOK_ID))
        );
        var ids = List.of(FIRST_BOOK_ID, SECOND_BOOK_ID);

        assertThat(repository.findAllById(ids))
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedBooks);
    }

    @Test
    @DisplayName("Проверяем возврат количесва элементов в таблице")
    void count() {
        assertEquals(FIND_ALL_EXPECTED_RESULT_COUNT, repository.count());
    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление по ID")
    void deleteById() {
        repository.deleteById(FIRST_BOOK_ID);
        assertNull(em.find(Book.class, FIRST_BOOK_ID));
    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление одной записи")
    void delete() {
        var bookToDelete = em.find(Book.class, FIRST_BOOK_ID);

        assertNotNull(bookToDelete);

        repository.delete(bookToDelete);
        em.flush();
        em.clear();

        assertNull(em.find(Book.class, FIRST_BOOK_ID));
    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление по коллекции ID")
    void deleteAllById() {
        var ids = List.of(FIRST_BOOK_ID, SECOND_BOOK_ID);
        assertNotNull(em.find(Book.class, FIRST_BOOK_ID));
        assertNotNull(em.find(Book.class, SECOND_BOOK_ID));

        repository.deleteAllById(ids);
        em.clear();

        assertNull(em.find(Book.class, FIRST_BOOK_ID));
        assertNull(em.find(Book.class, SECOND_BOOK_ID));
    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление всех записей")
    void deleteAll() {
        assertNotEquals(0, repository.count());

        repository.deleteAll();
        em.clear();

        assertEquals(0, repository.count());
    }

    @Test
    @Rollback
    @DisplayName("Проверяем удаление коллекции записей")
    void testDeleteAll() {
        var book1 = em.find(Book.class, FIRST_BOOK_ID);
        var book2 = em.find(Book.class, SECOND_BOOK_ID);
        assertNotNull(book1);
        assertNotNull(book2);

        var booksForDelete = List.of(book1, book2);

        repository.deleteAll(booksForDelete);
        em.clear();

        assertNull(em.find(Book.class, FIRST_BOOK_ID));
        assertNull(em.find(Book.class, SECOND_BOOK_ID));
    }

    private void setNextBookSequence(Long n) {
        em.getEntityManager()
                .createNativeQuery("alter sequence public.book_sequence_id restart with :n")
                .setParameter("n", n)
                .executeUpdate();
    }

    private void setNextBookCommentSequence(Long n) {
        em.getEntityManager()
                .createNativeQuery("alter sequence public.book_comment_sequence_id restart with :n")
                .setParameter("n", n)
                .executeUpdate();
    }

    private Set<Author> getAuthorsListForTest() {
        return Set.of(
                new Author().setId(FIRST_AUTHOR_ID).setFirstName(FIRST_AUTHOR_NAME).setSecondName(FIRST_AUTHOR_NAME),
                new Author().setId(SECOND_AUTHOR_ID).setFirstName(SECOND_AUTHOR_NAME).setSecondName(SECOND_AUTHOR_NAME)
        );
    }

    private Genre getCrimeGenreForTest() {
        return new Genre()
                .setId(CRIME_GENRE_ID)
                .setCode(CRIME_GENRE_CODE);
    }

    private Genre getDetectiveGenreForTest() {
        return new Genre()
                .setId(DETECTIVE_GENRE_ID)
                .setCode(DETECTIVE_GENRE_CODE);
    }

    private Set<BookComment> getCommentsForBook(Long bookId) {
        if (bookId.equals(FIRST_BOOK_ID)) {
            Set<BookComment> comments = new HashSet<>();
            comments.add(new BookComment().setId(FIRST_BOOK_COMMENT1_ID).setBook(em.find(Book.class, FIRST_BOOK_ID)).setText(FIRST_BOOK_COMMENT1));
            comments.add(new BookComment().setId(FIRST_BOOK_COMMENT2_ID).setBook(em.find(Book.class, FIRST_BOOK_ID)).setText(FIRST_BOOK_COMMENT2));
            return comments;
        } else if (bookId.equals(SECOND_BOOK_ID)) {
            Set<BookComment> comments = new HashSet<>();
            comments.add(new BookComment().setId(SECOND_BOOK_COMMENT1_ID).setBook(em.find(Book.class, SECOND_BOOK_ID)).setText(SECOND_BOOK_COMMENT1));
            comments.add(new BookComment().setId(SECOND_BOOK_COMMENT2_ID).setBook(em.find(Book.class, SECOND_BOOK_ID)).setText(SECOND_BOOK_COMMENT2));
            comments.add(new BookComment().setId(SECOND_BOOK_COMMENT3_ID).setBook(em.find(Book.class, SECOND_BOOK_ID)).setText(SECOND_BOOK_COMMENT3));
            return comments;
        } else if (bookId.equals(NEW_BOOK3_ID)) {
            return Set.of(
                    new BookComment().setId(START_BOOK_COMMENT_SEQUENCE).setBook(new Book().setId(NEW_BOOK3_ID)).setText(FIRST_BOOK_COMMENT1)
            );
        } else {
            return Set.of();
        }
    }
}