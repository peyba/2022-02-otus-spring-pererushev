package ru.otus.homework05.services;

import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    Book saveBook(Book book);
    List<Book> getAllBooks();
    Optional<Book> findBookById(Long id);
    void deleteBookById(Long id);

    List<Genre> getAllGenres();
    Optional<Genre> findGenreById(Long id);

    List<Author> getAllAuthors();
    Optional<Author> findAuthorById(Long id);
}
