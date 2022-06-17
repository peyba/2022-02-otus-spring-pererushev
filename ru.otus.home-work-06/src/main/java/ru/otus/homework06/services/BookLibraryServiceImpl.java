package ru.otus.homework06.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.domain.BookComment;
import ru.otus.homework06.repository.BookCommentRepository;
import ru.otus.homework06.repository.BookRepository;
import ru.otus.homework06.domain.Book;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class BookLibraryServiceImpl implements LibraryBookService {

    private final BookRepository bookRepository;
    private final BookCommentRepository commentRepository;

    @Override
    @Transactional
    public Book save(Book book) {
        if(book.getAuthors() == null) {
            book.setAuthors(Set.of());
        }

        if (book.getBookComments() == null) {
            book.setBookComments(Set.of());
        }

        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BookComment> getAllComments(Long bookId) {
        var book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            return Set.of();
        } else {
            initialiseLazyCollection(book.get().getBookComments());
            return book.get().getBookComments();
        }
    }

    @Override
    public Optional<BookComment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        var comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            var comm = comment.get();
            comm.getBook().getBookComments().remove(comm);
            commentRepository.delete(comm);
        } else {
            throw new LibraryEntitySavingException("Can't find comment. id: " + id);
        }
    }

    private void initialiseLazyCollection(Collection<?> collection) {
        collection.size();
    }
}