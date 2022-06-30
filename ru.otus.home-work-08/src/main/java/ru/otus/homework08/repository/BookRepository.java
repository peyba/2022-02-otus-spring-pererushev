package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.domain.Book;

import java.math.BigInteger;

public interface BookRepository extends MongoRepository<Book, BigInteger> {
}
