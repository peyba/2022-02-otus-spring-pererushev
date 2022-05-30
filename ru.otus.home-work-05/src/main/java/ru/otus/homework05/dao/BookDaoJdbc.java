package ru.otus.homework05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

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
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(
                            "SELECT id, name, genre_id FROM public.book WHERE id = :id",
                            Map.of("id", id),
                            new BookMapper()
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
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
                "SELECT id, name, genre_id FROM public.book",
                new BookMapper()
        );
    }

    @Override
    public List<Book> findAllById(Iterable<Long> ids) {
        return jdbc.query(
                "SELECT id, name, genre_id FROM public.book WHERE id IN(:id)",
                Map.of("id", ids),
                new BookMapper()
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

    private void deleteAllAuthorsLinkById(List<Long> ids){
        jdbc.update(
                "DELETE FROM public.book_authors WHERE book_id IN(:ids)",
                Map.of("ids", ids)
        );
    }

    private void deleteAllAuthorsLink(){
        jdbc.update(
                "DELETE FROM public.book_authors",
                Map.of()
        );
    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var genre = genreDao.findById(rs.getLong("genre_id")).orElseThrow();
            var authors = authorDao.findAllById(getBoorAuthorsIdsById(rs.getLong("id")));
            return new Book()
                    .setId(rs.getLong("id"))
                    .setName(rs.getString("name"))
                    .setGenre(genre)
                    .setAuthors(authors);
        }
    }
}