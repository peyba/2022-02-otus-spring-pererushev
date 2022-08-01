package ru.otus.homework08.mongock.changelog.test;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework08.domain.Author;
import ru.otus.homework08.domain.Book;
import ru.otus.homework08.domain.BookComment;
import ru.otus.homework08.domain.Genre;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.repository.BookCommentRepository;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.repository.GenreRepository;

import java.util.List;
import java.util.Set;

@ChangeLog(order = "v1.0")
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "a.pererushev", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.getCollection("books").drop();
        db.getCollection("book_comments").drop();
        db.getCollection("database_sequences").drop();
        db.getCollection("genres").drop();
        db.getCollection("authors").drop();
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "a.pererushev", runAlways = true)
    public void addAuthors(AuthorRepository repository) {
        List<Author> authorList = List.of(
                new Author().setId(1L).setFirstName("Author1").setSecondName("Author1"),
                new Author().setId(2L).setFirstName("Author2").setSecondName("Author2")
        );

        repository.saveAll(authorList);
    }

    @ChangeSet(order = "003", id = "addGenres", author = "a.pererushev", runAlways = true)
    public void addGenres(GenreRepository repository) {
        var genres = List.of(
            new Genre().setId(1L).setCode("CRIME"),
            new Genre().setId(2L).setCode("DETECTIVE")
        );

        repository.saveAll(genres);
    }

    @ChangeSet(order = "004", id = "addBooks", author = "a.pererusehv", runAlways = true)
    public void addBooks(
            BookRepository bookRepository,
            GenreRepository genreRepository,
            AuthorRepository authorRepository
    ) {
        var boos = List.of(
                new Book()
                        .setName("book1")
                        .setGenre(genreRepository.findById(1L).orElseThrow())
                        .setAuthors(
                                Set.of(
                                        authorRepository.findById(1L).orElseThrow(),
                                        authorRepository.findById(2L).orElseThrow()
                                )
                        ),
                new Book()
                        .setName("book2")
                        .setGenre(genreRepository.findById(2L).orElseThrow())
                        .setAuthors(Set.of())
        );

        bookRepository.saveAll(boos);
    }

    @ChangeSet(order = "005", id = "addBookComments", author = "a.pererusehv", runAlways = true)
    public void addBookComments(BookRepository bookRepository, BookCommentRepository bookCommentRepository) {
        var book1 = bookRepository.findById(1L);
        var book2 = bookRepository.findById(2L);
        List<BookComment> comments = List.of(
                new BookComment().setBook(book1.orElseThrow()).setText("Good, but short"),
                new BookComment().setBook(book1.orElseThrow()).setText("Out of stock"),
                new BookComment().setBook(book2.orElseThrow()).setText("Out of stock"),
                new BookComment().setBook(book2.orElseThrow()).setText("Very boooooriiiiing!!!"),
                new BookComment().setBook(book2.orElseThrow()).setText("Got it.")
        );

        bookCommentRepository.saveAll(comments);
    }
}