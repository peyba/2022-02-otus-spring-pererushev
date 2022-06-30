package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.domain.BookComment;

public interface BookCommentRepository extends MongoRepository<BookComment, Long> {
}