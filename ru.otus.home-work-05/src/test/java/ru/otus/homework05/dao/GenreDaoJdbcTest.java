package ru.otus.homework05.dao;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework05.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreDaoJdbc.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса GenreDaoJdbc")
class GenreDaoJdbcTest {

    private static final int FIND_ALL_EXPECTED_RESULT_COUNT = 2;
    private static final Long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_CODE = "CRIME";
    private static final Long SECOND_GENRE_ID = 2L;
    private static final String SECOND_GENRE_CODE = "DETECTIVE";

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    @Test
    @DisplayName("Проверяем на совпадение всех записей в таблице")
    void findAll() {
        List<Genre> expectedGenres = List.of(
                new Genre().setId(FIRST_GENRE_ID).setCode(FIRST_GENRE_CODE),
                new Genre().setId(SECOND_GENRE_ID).setCode(SECOND_GENRE_CODE)
        );

        GenreDao dao = new GenreDaoJdbc(jdbc);
        assertThat(dao.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedGenres);

    }

    @Test
    @DisplayName("Проверяем количество возвнащаемых строк")
    void count() {
        GenreDao dao = new GenreDaoJdbc(jdbc);
        assertEquals(FIND_ALL_EXPECTED_RESULT_COUNT, dao.count());
    }

    @Test
    @DisplayName("Проверяем на существование записи в таблице")
    void existsById() {
        GenreDao dao = new GenreDaoJdbc(jdbc);
        assertTrue(dao.existsById(FIRST_GENRE_ID));
    }

    @Test
    @DisplayName("Проверяем поиск записи по ID")
    void findById() {
        var expectedGenre = Optional.ofNullable(new Genre().setId(FIRST_GENRE_ID).setCode(FIRST_GENRE_CODE));

        GenreDao dao = new GenreDaoJdbc(jdbc);
        assertEquals(expectedGenre, dao.findById(FIRST_GENRE_ID));
    }
}