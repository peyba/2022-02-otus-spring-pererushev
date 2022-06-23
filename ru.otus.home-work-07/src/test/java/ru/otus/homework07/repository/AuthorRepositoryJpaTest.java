package ru.otus.homework07.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework07.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса AuthorDaoJdbc")
class AuthorRepositoryJpaTest {

    private static final int FIND_ALL_EXPECTED_RESULT_COUNT = 2;
    private static final Long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author1";
    private static final Long SECOND_AUTHOR_ID = 2L;
    private static final String SECOND_AUTHOR_NAME = "Author2";

    @Autowired
    private AuthorRepository dao;

    @Test
    @DisplayName("Проверяем на совпадение всех записей в таблице")
    void findAll() {
        List<Author> expectedAuthors = List.of(
                new Author().setId(FIRST_AUTHOR_ID)
                        .setFirstName(FIRST_AUTHOR_NAME)
                        .setSecondName(FIRST_AUTHOR_NAME),
                new Author().setId(SECOND_AUTHOR_ID)
                        .setFirstName(SECOND_AUTHOR_NAME)
                        .setSecondName(SECOND_AUTHOR_NAME)
        );

        assertThat(dao.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedAuthors);

    }

    @Test
    @DisplayName("Проверяем количество возвнащаемых строк")
    void count() {
        assertEquals(FIND_ALL_EXPECTED_RESULT_COUNT, dao.count());
    }

    @Test
    @DisplayName("Проверяем на существование записи в таблице")
    void existsById() {
        assertTrue(dao.existsById(FIRST_AUTHOR_ID));
    }

    @Test
    @DisplayName("Проверяем поиск записи по ID")
    void findById() {
        var expectedAuthor =
                Optional.ofNullable(new Author()
                        .setId(FIRST_AUTHOR_ID)
                        .setFirstName(FIRST_AUTHOR_NAME)
                        .setSecondName(FIRST_AUTHOR_NAME)
                );

        assertEquals(expectedAuthor, dao.findById(FIRST_AUTHOR_ID));
    }

    @Test
    @DisplayName("Проверяем поиск записей по списку ID")
    void findAllById() {
        List<Long> ids = List.of(FIRST_AUTHOR_ID, SECOND_AUTHOR_ID);
        List<Author> expectedAuthors = List.of(
                new Author().setId(FIRST_AUTHOR_ID)
                        .setFirstName(FIRST_AUTHOR_NAME)
                        .setSecondName(FIRST_AUTHOR_NAME),
                new Author().setId(SECOND_AUTHOR_ID)
                        .setFirstName(SECOND_AUTHOR_NAME)
                        .setSecondName(SECOND_AUTHOR_NAME)
        );

        assertThat(dao.findAllById(ids))
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedAuthors);
    }
}