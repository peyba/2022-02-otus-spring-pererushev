package ru.otus.homework06.services;

import ru.otus.homework06.dto.BookCommentDto;
import ru.otus.homework06.dto.BookDto;
import ru.otus.homework06.dto.BookTitleDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LibraryBookService {
    BookDto save(BookDto book);
    List<BookTitleDto> getAll();
    Optional<BookDto> findById(Long id);
    void deleteById(Long id);
    Set<BookCommentDto> getAllComments(Long bookId);
    Optional<BookCommentDto> findCommentById(Long id);
    void deleteCommentById(Long id);
}