package ru.otus.homework07.repository;

import ru.otus.homework07.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {
    BookComment save(BookComment comment);

    List<BookComment> saveAll(Iterable<BookComment> comments);

    Optional<BookComment> findById(Long id);

    boolean existsById(Long id);

    List<BookComment> findAll();

    List<BookComment> findAllById(Iterable<Long> ids);

    long count();

    void deleteById(Long id);

    void delete(BookComment comment);

    void deleteAllById(Iterable<Long> ids);

    void deleteAll(Iterable<BookComment> entities);

    void deleteAll();
}