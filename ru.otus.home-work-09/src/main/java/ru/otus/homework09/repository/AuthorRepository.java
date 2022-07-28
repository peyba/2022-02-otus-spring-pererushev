package ru.otus.homework09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework09.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
