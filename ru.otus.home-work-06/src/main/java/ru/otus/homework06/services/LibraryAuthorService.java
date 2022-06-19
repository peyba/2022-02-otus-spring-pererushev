package ru.otus.homework06.services;

import ru.otus.homework06.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface LibraryAuthorService {
    List<AuthorDto> getAll();
    Optional<AuthorDto> findById(Long id);
}
