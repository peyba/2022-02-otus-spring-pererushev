package ru.otus.homework05.dao;

import ru.otus.homework05.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> findAll();
    long count();
    boolean existsById(Long id);
    Optional<Author> findById(Long id);
    List<Author> findAllById(List<Long> id);
}
