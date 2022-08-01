package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.domain.BookComment;

import java.util.List;

public interface BookCommentRepository extends MongoRepository<BookComment, Long> {
    List<BookComment> deleteByBook_Id(Long bookId);
    List<BookComment> findByBook_Id(Long bookId);
}