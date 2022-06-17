package ru.otus.homework06.services;

import ru.otus.homework06.domain.Author;

import java.util.List;
import java.util.Optional;

public interface LibraryAuthorService {
    List<Author> getAll();
    Optional<Author> findById(Long id);
}
