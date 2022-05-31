package ru.otus.homework05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Genre;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    @Transactional
    public Book save(Book book) {
        if (book.getId() != null) {
            return update(book);
        } else {
            return insert(book);
        }
    }


    @Override
    @Transactional
    public List<Book> saveAll(Iterable<Book> books) {
        List<Book> newBooks = new ArrayList<>();
        for (var book : books) {
            newBooks.add(save(book));
        }
        return newBooks;
    }

    @Override
    public Optional<Book> findById(Long id) {
        var books = jdbc.query(
                "SELECT B.id, B.name, " +
                        "G.id AS genre_id, G.code AS genre_code, G.name_eng AS genre_name_eng, G.name_rus AS genre_name_rus, " +
                        "A.id AS author_id, A.first_name AS author_first_name, A.second_name AS author_second_name " +
                        "FROM public.book B " +
                        "INNER JOIN public.genre G ON G.id = B.genre_id " +
                        "LEFT JOIN public.book_authors BA ON BA.book_id = B.id " +
                        "LEFT JOIN public.author A ON A.id = BA.author_id " +
                        "WHERE B.id = :id",
                Map.of("id", id),
                new BookResultSetExtractor()
        );

        if (books == null) {
            return Optional.empty();
        }

        return books.stream().findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT case when count(*) = 0 then false else true end as count FROM public.book WHERE id = :id",
                Map.of("id", id),
                Boolean.class
        ));
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query(
                "SELECT B.id, B.name, " +
                        "G.id AS genre_id, G.code AS genre_code, G.name_eng AS genre_name_eng, G.name_rus AS genre_name_rus, " +
                        "A.id AS author_id, A.first_name AS author_first_name, A.second_name AS author_second_name " +
                        "FROM public.book B " +
                        "INNER JOIN public.genre G ON G.id = B.genre_id " +
                        "LEFT JOIN public.book_authors BA ON BA.book_id = B.id " +
                        "LEFT JOIN public.author A ON A.id = BA.author_id ",
                new BookResultSetExtractor()
        );
    }

    @Override
    public List<Book> findAllById(Iterable<Long> ids) {
        return jdbc.query(
                "SELECT B.id, B.name, " +
                        "G.id AS genre_id, G.code AS genre_code, G.name_eng AS genre_name_eng, G.name_rus AS genre_name_rus, " +
                        "A.id AS author_id, A.first_name AS author_first_name, A.second_name AS author_second_name " +
                        "FROM public.book B " +
                        "INNER JOIN public.genre G ON G.id = B.genre_id " +
                        "LEFT JOIN public.book_authors BA ON BA.book_id = B.id " +
                        "LEFT JOIN public.author A ON A.id = BA.author_id " +
                        "WHERE B.id IN(:ids)",
                Map.of("ids", ids),
                new BookResultSetExtractor()
        );
    }

    @Override
    public long count() {
        return jdbc.queryForObject(
                "SELECT count(*) as count FROM public.book",
                Map.of(),
                Integer.class
        );
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        deleteAllAuthorsLinkById(List.of(id));
        jdbc.update(
                "DELETE FROM public.book WHERE id = :id",
                Map.of("id", id)
        );
    }

    @Override
    @Transactional
    public void delete(Book book) {
        deleteAllAuthorsLinkById(List.of(book.getId()));
        jdbc.update(
                "DELETE FROM public.book WHERE id = :id",
                getParameters(book)
        );
    }

    @Override
    @Transactional
    public void deleteAllById(Iterable<Long> ids) {
        var idList = new ArrayList<Long>();
        ids.forEach(idList::add);
        deleteAllAuthorsLinkById(idList);

        jdbc.update(
                "DELETE FROM public.book WHERE id IN(:id)",
                Map.of("id", ids)
        );
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<Book> books) {
        var idList = new ArrayList<Long>();
        books.forEach(b -> idList.add(b.getId()));
        deleteAllAuthorsLinkById(idList);

        jdbc.update(
                "DELETE FROM public.book WHERE id IN(:ids)",
                Map.of("ids", getIdsByBooks(books))
        );
    }

    @Override
    @Transactional
    public void deleteAll() {
        deleteAllAuthorsLink();
        jdbc.update(
                "DELETE FROM public.book",
                Map.of()
        );
    }

    private List<Long> getBoorAuthorsIdsById(Long id) {
        return jdbc.queryForList(
                "SELECT author_id FROM public.book_authors WHERE book_id = :id",
                Map.of("id", id),
                Long.class
        );
    }

    private List<Long> findAllBoorAuthorsIdsById(List<Long> ids) {
        return jdbc.queryForList(
                "SELECT author_id FROM public.book_authors WHERE book_id IN(:ids)",
                Map.of("ids", ids),
                Long.class
        );
    }

    private Book insert(Book book) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(
                "INSERT INTO public.book(name, genre_id)  VALUES(:name, :genre_id);",
                getParameters(book),
                kh
        );

        book.setId(kh.getKey().longValue());

        saveAuthorLink(book);

        return book;
    }

    private Book update(Book book) {
        jdbc.update("UPDATE public.book SET name = :name, genre_id = :genre_id WHERE id = :id;", getParameters(book));
        return book;
    }

    private MapSqlParameterSource getParameters(Book book) {
        var params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("name", book.getName());
        params.addValue("genre_id", book.getGenre().getId());
        return params;
    }

    private List<Long> getIdsByBooks(Iterable<Book> books) {
        List<Long> ids = new ArrayList<>();
        for (var book : books) {
            ids.add(book.getId());
        }
        return ids;
    }

    private void saveAuthorLink(Book book) {
        var ids = new ArrayList<Long>();
        book.getAuthors().stream().map(Author::getId).forEach(ids::add);

        jdbc.update(
                "INSERT INTO public.book_authors(book_id, author_id) SELECT :book_id, id FROM public.author WHERE id IN(:author_id)",
                Map.of(
                        "book_id", book.getId(),
                        "author_id", ids
                )
        );
    }

    private void deleteAllAuthorsLinkById(List<Long> ids) {
        jdbc.update(
                "DELETE FROM public.book_authors WHERE book_id IN(:ids)",
                Map.of("ids", ids)
        );
    }

    private void deleteAllAuthorsLink() {
        jdbc.update(
                "DELETE FROM public.book_authors",
                Map.of()
        );
    }

    private static class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {
        @Override
        @NotNull
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            var bookMap = new HashMap<Long, Book>();

            while (rs.next()) {
                var bookId = rs.getLong("id");

                if (!bookMap.containsKey(rs.getLong("id"))) {
                    bookMap.put(bookId, createBookFromResultSet(rs));
                }

                if (hasAuthor(rs)) {
                    bookMap.get(bookId).getAuthors().add(createAuthorFromResultSet(rs));
                }
            }

            return List.copyOf(bookMap.values());
        }

        private boolean hasAuthor(ResultSet rs) throws SQLException {
            return rs.getObject("author_id", Long.class) != null;
        }

        private Book createBookFromResultSet(ResultSet rs) throws SQLException {
            return new Book()
                    .setId(rs.getLong("id"))
                    .setName(rs.getString("name"))
                    .setGenre(createGenreFromResultSet(rs))
                    .setAuthors(new ArrayList<>());
        }

        private Genre createGenreFromResultSet(ResultSet rs) throws SQLException {
            return new Genre()
                    .setId(rs.getLong("genre_id"))
                    .setCode(rs.getString("genre_code"))
                    .setNameEng(rs.getString("genre_name_eng"))
                    .setNameRus(rs.getString("genre_name_rus"));
        }

        private Author createAuthorFromResultSet(ResultSet rs) throws SQLException {
            return new Author()
                    .setId(rs.getLong("author_id"))
                    .setFirstName(rs.getString("author_first_name"))
                    .setSecondName(rs.getString("author_second_name"));
        }
    }
}