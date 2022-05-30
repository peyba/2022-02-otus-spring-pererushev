package ru.otus.homework05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Genre> findAll() {
        return jdbc.query(
                "SELECT id, code, name_eng, name_rus FROM public.genre",
                new GenreDaoJdbc.GenreMapper()
        );
    }

    @Override
    public long count() {
        return jdbc.queryForObject(
                "SELECT count(*) as count FROM public.genre",
                Map.of(),
                Integer.class
        );
    }

    @Override
    public boolean existsById(Long id) {
        return Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT case when count(*) = 0 then false else true end as count FROM public.genre WHERE id = :id",
                Map.of("id", id),
                Boolean.class
        ));
    }

    @Override
    public Optional<Genre> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(
                            "SELECT id, code, name_eng, name_rus FROM public.genre WHERE id = :id",
                            Map.of("id", id),
                            new GenreDaoJdbc.GenreMapper()
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre()
                    .setId(rs.getLong("id"))
                    .setCode(rs.getString("code"))
                    .setNameEng(rs.getString("name_eng"))
                    .setNameRus(rs.getString("name_rus"));
        }
    }
}
