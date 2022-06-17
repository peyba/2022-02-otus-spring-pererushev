package ru.otus.homework06.services;

import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.BookComment;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LibraryBookService {
    Book save(Book book);
    List<Book> getAll();
    Optional<Book> findById(Long id);
    void deleteById(Long id);
    Set<BookComment> getAllComments(Long bookId);
    Optional<BookComment> findCommentById(Long id);
    void deleteCommentById(Long id);
}