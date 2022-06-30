package ru.otus.homework08.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework08.domain.BookComment;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
}