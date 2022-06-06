package ru.otus.homework05.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework05.dao.AuthorDao;
import ru.otus.homework05.dao.BookDao;
import ru.otus.homework05.dao.GenreDao;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Genre;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    // region Books operations

    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        bookDao.deleteById(id);
    }
    // endregion

    //region Genre operations

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.findAll();
    }

    @Override
    public Optional<Genre> findGenreById(Long id) {
        return genreDao.findById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        return authorDao.findById(id);
    }
    // endregion
}