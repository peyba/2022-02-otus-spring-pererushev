package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.domain.Book;

public interface BookRepository extends MongoRepository<Book, Long> {
}
