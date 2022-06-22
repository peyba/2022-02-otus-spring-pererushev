package ru.otus.homework06.repository;

import ru.otus.homework06.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();
    long count();
    boolean existsById(Long id);
    Optional<Author> findById(Long id);
    List<Author> findAllById(List<Long> id);
}
