package ru.otus.homework07.services;

import ru.otus.homework07.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface LibraryGenreService {
    List<GenreDto> getAll();
    Optional<GenreDto> findById(Long id);
}