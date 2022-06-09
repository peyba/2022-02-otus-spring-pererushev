package ru.otus.homework06.services;

import ru.otus.homework06.domain.Author;
import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.BookComment;
import ru.otus.homework06.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LibraryService {
    Book saveBook(Book book);
    List<Book> getAllBooks();
    Optional<Book> findBookById(Long id);
    void deleteBookById(Long id);

    List<Genre> getAllGenres();
    Optional<Genre> findGenreById(Long id);

    List<Author> getAllAuthors();
    Optional<Author> findAuthorById(Long id);

    void deleteBookComment(Long id);
    Set<BookComment> getBookComments(Long bookId);
}