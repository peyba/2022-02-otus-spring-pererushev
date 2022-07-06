package ru.otus.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework07.domain.BookComment;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
}