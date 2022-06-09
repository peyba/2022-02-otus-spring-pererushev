package ru.otus.homework06.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.domain.BookComment;
import ru.otus.homework06.repository.AuthorRepository;
import ru.otus.homework06.repository.BookCommentRepository;
import ru.otus.homework06.repository.BookRepository;
import ru.otus.homework06.repository.GenreRepository;
import ru.otus.homework06.domain.Author;
import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookCommentRepository commentRepository;

    // region Books operations

    @Override
    @Transactional
    public Book saveBook(Book book) {
        if(book.getAuthors() == null) {
            book.setAuthors(Set.of());
        }

        if (book.getBookComments() == null) {
            book.setBookComments(Set.of());
        }

        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Set<BookComment> getBookComments(Long bookId) {
        var book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            return Set.of();
        }
        return book.get().getBookComments();
    }
    // endregion

    //region Genre operations

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteBookComment(Long id) {
        var comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            var comm = comment.get();
            comm.getBook().getBookComments().remove(comm);
            commentRepository.delete(comm);
        } else {
            throw new BookSavingException("Can't find comment. id: " + id);
        }
    }
    // endregion
}