package ru.otus.homework08.services;

import ru.otus.homework08.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorLibraryService {
    List<AuthorDto> getAll();
    Optional<AuthorDto> findById(Long id);
}
