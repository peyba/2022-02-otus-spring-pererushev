package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, Long> {
}