package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, Long> {
}
