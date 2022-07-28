package ru.otus.homework09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework09.domain.BookComment;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
}