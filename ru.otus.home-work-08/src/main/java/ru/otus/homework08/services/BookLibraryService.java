package ru.otus.homework08.services;

import ru.otus.homework08.dto.BookCommentDto;
import ru.otus.homework08.dto.BookDto;
import ru.otus.homework08.dto.BookTitleDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookLibraryService {
    BookDto save(BookDto book);
    List<BookTitleDto> getAll();
    Optional<BookDto> findById(Long id);
    void deleteById(Long id);
    Set<BookCommentDto> getAllComments(Long bookId);
    Optional<BookCommentDto> findCommentById(Long id);
    void deleteCommentById(Long id);
}