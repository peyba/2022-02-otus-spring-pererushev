package ru.otus.homework06.services;

import ru.otus.homework06.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryGenreService {
    List<Genre> getAll();
    Optional<Genre> findById(Long id);
}