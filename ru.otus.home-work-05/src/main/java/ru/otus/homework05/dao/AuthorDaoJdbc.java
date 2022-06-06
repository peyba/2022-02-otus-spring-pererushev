package ru.otus.homework05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> findAll() {
        return jdbc.query(
                "SELECT id, first_name, second_name FROM public.author",
                new AuthorDaoJdbc.AuthorMapper()
        );
    }

    @Override
    public long count() {
        return jdbc.queryForObject(
                "SELECT count(*) as count FROM public.author",
                Map.of(),
                Integer.class
        );
    }

    @Override
    public boolean existsById(Long id) {
        return Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT case when count(*) = 0 then false else true end as count FROM public.author WHERE id = :id",
                Map.of("id", id),
                Boolean.class
        ));
    }

    @Override
    public Optional<Author> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(
                            "SELECT id, first_name, second_name FROM public.author WHERE id = :id",
                            Map.of("id", id),
                            new AuthorDaoJdbc.AuthorMapper()
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findAllById(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }

        return jdbc.query(
                "SELECT id, first_name, second_name FROM public.author WHERE id IN(:ids)",
                Map.of("ids", ids),
                new AuthorDaoJdbc.AuthorMapper()
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author()
                    .setId(rs.getLong("id"))
                    .setFirstName(rs.getString("first_name"))
                    .setSecondName(rs.getString("second_name"));
        }
    }
}