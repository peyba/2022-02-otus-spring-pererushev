package ru.otus.homework08.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework08.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
