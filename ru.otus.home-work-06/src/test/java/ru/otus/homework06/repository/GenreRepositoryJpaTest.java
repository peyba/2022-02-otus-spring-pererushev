package ru.otus.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework06.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса GenreDaoJdbc")
class GenreRepositoryJpaTest {

    private static final int FIND_ALL_EXPECTED_RESULT_COUNT = 2;
    private static final Long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_CODE = "CRIME";
    private static final Long SECOND_GENRE_ID = 2L;
    private static final String SECOND_GENRE_CODE = "DETECTIVE";

    @Autowired
    private GenreRepositoryJpa repositoryJpa;


    @Test
    @DisplayName("Проверяем на совпадение всех записей в таблице")
    void findAll() {
        List<Genre> expectedGenres = List.of(
                new Genre().setId(FIRST_GENRE_ID).setCode(FIRST_GENRE_CODE),
                new Genre().setId(SECOND_GENRE_ID).setCode(SECOND_GENRE_CODE)
        );

        assertThat(repositoryJpa.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedGenres);

    }

    @Test
    @DisplayName("Проверяем количество возвнащаемых строк")
    void count() {
        assertEquals(FIND_ALL_EXPECTED_RESULT_COUNT, repositoryJpa.count());
    }

    @Test
    @DisplayName("Проверяем на существование записи в таблице")
    void existsById() {
        assertTrue(repositoryJpa.existsById(FIRST_GENRE_ID));
    }

    @Test
    @DisplayName("Проверяем поиск записи по ID")
    void findById() {
        var expectedGenre = Optional.ofNullable(new Genre().setId(FIRST_GENRE_ID).setCode(FIRST_GENRE_CODE));

        assertEquals(expectedGenre, repositoryJpa.findById(FIRST_GENRE_ID));
    }
}