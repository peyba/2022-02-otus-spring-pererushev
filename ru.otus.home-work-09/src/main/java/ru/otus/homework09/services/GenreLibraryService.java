package ru.otus.homework09.services;

import ru.otus.homework09.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreLibraryService {
    List<GenreDto> getAll();
    Optional<GenreDto> findById(Long id);
}