package ru.otus.homework05.dao;

import ru.otus.homework05.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
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
