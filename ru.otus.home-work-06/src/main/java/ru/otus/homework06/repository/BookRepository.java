package ru.otus.homework06.repository;

import ru.otus.homework06.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    List<Book> saveAll(Iterable<Book> books);

    Optional<Book> findById(Long id);

    boolean existsById(Long id);

    List<Book> findAll();

    List<Book> findAllById(Iterable<Long> ids);

    long count();

    void deleteById(Long id);

    void delete(Book book);

    void deleteAllById(Iterable<Long> ids);

    void deleteAll(Iterable<Book> entities);

    void deleteAll();
}
