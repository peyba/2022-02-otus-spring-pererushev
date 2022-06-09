package ru.otus.homework06.repository;

import ru.otus.homework06.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();
    long count();
    boolean existsById(Long id);
    Optional<Genre> findById(Long id);
}