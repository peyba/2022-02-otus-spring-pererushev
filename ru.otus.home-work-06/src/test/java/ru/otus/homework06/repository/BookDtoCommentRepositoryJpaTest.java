package ru.otus.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.BookComment;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BookCommentRepositoryJpa.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса BookCommentRepositoryJpa")
class BookDtoCommentRepositoryJpaTest {

    private static final Long FIRST_BOOK_ID = 1L;
    private static final Long SECOND_BOOK_ID = 2L;
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
    private static final Long FIRST_COMMENT_ID = 1L;
    private static final Long NOT_EXISTED_COMMENT_ID = 999L;
    private static final int FIRST_BOOK_COMMENTS_BEFORE = 2;
    private static final int FIRST_BOOK_COMMENTS_AFTER = 3;
    private static final Long NEW_COMMENT6_ID = 6L;
    private static final Long NEW_COMMENT7_ID = 7L;
    private static final String TEST_COMMENT_TEXT_1 = "test comment text";
    private static final String TEST_COMMENT_TEXT_2 = "test";
    private static final Long ALL_COMMENTS_COUNT = 5L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookCommentRepository repository;

    @Test
    @DisplayName("Проверяем сохранение одного элемента")
    @Rollback
    void save() {
        assertEquals(FIRST_BOOK_COMMENTS_BEFORE, em.find(Book.class, FIRST_BOOK_ID).getBookComments().size());

        var comment = new BookComment()
                .setBook(em.find(Book.class, FIRST_BOOK_ID))
                .setText(TEST_COMMENT_TEXT_1);

        var newCommentId = repository.save(comment).getId();
        em.flush();
        em.clear();

        assertEquals(TEST_COMMENT_TEXT_1, em.find(BookComment.class, newCommentId).getText());
        assertEquals(FIRST_BOOK_COMMENTS_AFTER, em.find(Book.class, FIRST_BOOK_ID).getBookComments().size());
        rollbackSequence(6);
    }

    @Test
    @DisplayName("Проверяем сохранение коллекции элементов")
    @Rollback
    void saveAll() {
        assertEquals(FIRST_BOOK_COMMENTS_BEFORE, em.find(Book.class, FIRST_BOOK_ID).getBookComments().size());

        var expectedComments = List.of(
                new BookComment()
                        .setId(NEW_COMMENT6_ID)
                        .setBook(em.find(Book.class, FIRST_BOOK_ID))
                        .setText(TEST_COMMENT_TEXT_1),
                new BookComment()
                        .setId(NEW_COMMENT7_ID)
                        .setBook(em.find(Book.class, SECOND_BOOK_ID))
                        .setText(TEST_COMMENT_TEXT_2)
        );

        var newComments = List.of(
                new BookComment()
                        .setBook(em.find(Book.class, FIRST_BOOK_ID))
                        .setText(TEST_COMMENT_TEXT_1),
                new BookComment()
                        .setBook(em.find(Book.class, SECOND_BOOK_ID))
                        .setText(TEST_COMMENT_TEXT_2)
        );

        repository.saveAll(newComments);
        em.flush();
        em.clear();

        var savedComments = new ArrayList<>();
        savedComments.add(em.find(BookComment.class, NEW_COMMENT6_ID));
        savedComments.add(em.find(BookComment.class, NEW_COMMENT7_ID));

        assertThat(savedComments).containsAll(expectedComments);

        rollbackSequence(6);
    }

    @Test
    @DisplayName("Проверяем существование по ID")
    void existsById() {
        assertFalse(repository.existsById(NOT_EXISTED_COMMENT_ID));
        assertTrue(repository.existsById(FIRST_COMMENT_ID));
    }

    @Test
    @DisplayName("Проверяем поиск всех элементов")
    void findAll() {
        var expectedComments = List.of(
                new BookComment().setId(FIRST_BOOK_COMMENT1_ID).setBook(em.find(Book.class, FIRST_BOOK_ID)).setText(FIRST_BOOK_COMMENT1),
                new BookComment().setId(FIRST_BOOK_COMMENT2_ID).setBook(em.find(Book.class, FIRST_BOOK_ID)).setText(FIRST_BOOK_COMMENT2),
                new BookComment().setId(SECOND_BOOK_COMMENT1_ID).setBook(em.find(Book.class, SECOND_BOOK_ID)).setText(SECOND_BOOK_COMMENT1),
                new BookComment().setId(SECOND_BOOK_COMMENT2_ID).setBook(em.find(Book.class, SECOND_BOOK_ID)).setText(SECOND_BOOK_COMMENT2),
                new BookComment().setId(SECOND_BOOK_COMMENT3_ID).setBook(em.find(Book.class, SECOND_BOOK_ID)).setText(SECOND_BOOK_COMMENT3)
        );

        var foundedComments = repository.findAll();

        assertThat(foundedComments).containsAll(expectedComments);
    }

    @Test
    @DisplayName("Проверка поиск по ID")
    void findAllById() {
        var expectedComments = List.of(
                new BookComment().setId(FIRST_BOOK_COMMENT1_ID).setBook(em.find(Book.class, FIRST_BOOK_ID)).setText(FIRST_BOOK_COMMENT1),
                new BookComment().setId(SECOND_BOOK_COMMENT2_ID).setBook(em.find(Book.class, SECOND_BOOK_ID)).setText(SECOND_BOOK_COMMENT2)
        );

        var foundedComments = repository.findAllById(List.of(FIRST_BOOK_COMMENT1_ID, SECOND_BOOK_COMMENT2_ID));

        assertThat(foundedComments).containsAll(expectedComments);
    }

    @Test
    @DisplayName("Проверка расчета количества")
    void count() {
        assertEquals(ALL_COMMENTS_COUNT, repository.count());
    }

    @Test
    @Rollback
    @DisplayName("Проверка удаления элемента по ID")
    void deleteById() {
        assertNotNull(em.find(BookComment.class, FIRST_BOOK_COMMENT1_ID));

        repository.deleteById(FIRST_BOOK_COMMENT1_ID);
        em.flush();
        em.clear();

        assertNull(em.find(BookComment.class, FIRST_BOOK_COMMENT1_ID));
        assertNotNull(em.find(Book.class, FIRST_BOOK_ID));
    }

    @Test
    @Rollback
    @DisplayName("Проверка удаления элемена")
    void delete() {
        var commentForDelete = em.find(BookComment.class, FIRST_BOOK_COMMENT1_ID);
        assertNotNull(commentForDelete);

        commentForDelete.getBook().getBookComments().remove(commentForDelete);
        repository.delete(commentForDelete);
        em.flush();
        em.clear();

        assertNull(em.find(BookComment.class, FIRST_BOOK_COMMENT1_ID));
        assertNotNull(em.find(Book.class, FIRST_BOOK_ID));
    }

    @Test
    @DisplayName("Провверка удаления элементов по коллекции ID")
    @Rollback
    void deleteAllById() {
        assertNotNull(em.find(BookComment.class, FIRST_BOOK_COMMENT1_ID));
        assertNotNull(em.find(BookComment.class, FIRST_BOOK_COMMENT2_ID));

        repository.deleteAllById(List.of(FIRST_BOOK_COMMENT1_ID, FIRST_BOOK_COMMENT2_ID));
        em.clear();

        assertNull(em.find(BookComment.class, FIRST_BOOK_COMMENT1_ID));
        assertNull(em.find(BookComment.class, FIRST_BOOK_COMMENT2_ID));
        assertNotNull(em.find(Book.class, FIRST_BOOK_ID));
    }

    @Test
    @DisplayName("Проверка удаление элементов по коллекции элементов")
    @Rollback
    void deleteAll() {
        var comment1 = em.find(BookComment.class, FIRST_BOOK_COMMENT1_ID);
        var comment2 = em.find(BookComment.class, FIRST_BOOK_COMMENT2_ID);
        assertNotNull(comment1);
        assertNotNull(comment2);

        repository.deleteAll(List.of(comment1, comment2));
        em.clear();

        assertNull(em.find(BookComment.class, FIRST_BOOK_COMMENT1_ID));
        assertNull(em.find(BookComment.class, FIRST_BOOK_COMMENT2_ID));
        assertNotNull(em.find(Book.class, FIRST_BOOK_ID));
    }

    @Test
    @DisplayName("Проверка удаления всех элементов")
    @Rollback
    void testDeleteAll() {
        assertEquals(ALL_COMMENTS_COUNT, repository.findAll().size());

        repository.deleteAll();
        em.clear();

        assertEquals(0, repository.findAll().size());
    }

    private void rollbackSequence(int n) {
        em.getEntityManager()
                .createNativeQuery("alter sequence public.book_comment_sequence_id restart with :n")
                .setParameter("n", n)
                .executeUpdate();
    }
}