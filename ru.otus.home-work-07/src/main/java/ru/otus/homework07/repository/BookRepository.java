package ru.otus.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework07.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
