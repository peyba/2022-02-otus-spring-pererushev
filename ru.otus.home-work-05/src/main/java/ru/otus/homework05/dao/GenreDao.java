package ru.otus.homework05.dao;

import ru.otus.homework05.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAll();
    long count();
    boolean existsById(Long id);
    Optional<Genre> findById(Long id);
}