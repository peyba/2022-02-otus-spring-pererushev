package ru.otus.homework09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework09.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}