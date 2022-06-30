package ru.otus.homework08.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework08.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}