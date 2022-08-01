package ru.otus.homework09.services;

import ru.otus.homework09.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorLibraryService {
    List<AuthorDto> getAll();
    List<AuthorDto> findAllById(List<Long> ids);
    Optional<AuthorDto> findById(Long id);
}
