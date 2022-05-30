package ru.otus.homework05.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.homework05.decorators.EntityListDecorator;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Genre;
import ru.otus.homework05.services.LibraryService;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private static final String BOOK_CONTEXT_OBJECT_KEY = "BOOK";
    private static final String NOT_SAVED_STATUS = "Not saved!";
    private static final String SAVED_STATUS = "Saved!";
    private static final String DELETED_STATUS = "Deleted!";

    private final ShellContext shellContext;
    private final LibraryService library;
    private final EntityListDecorator<Book> bookListDecorator;
    private final EntityListDecorator<Genre> genreListDecorator;
    private final EntityListDecorator<Author> authorListDecorator;

    // region Book creating/editing mode
    @ShellMethod(value = "Save book and go in to the root mode", key = {"book-save"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String saveBook() {
        var bookObject = shellContext.getObject(BOOK_CONTEXT_OBJECT_KEY, Book.class);
        var book = library.saveBook(bookObject);
        shellContext.drop();
        return book.toString() +
                System.lineSeparator() +
                SAVED_STATUS;
    }

    @ShellMethod(value = "Set genre to the book", key = {"book-genre-set"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String bookSetGenre(Long id) {
        var bookObject = shellContext.getObject(BOOK_CONTEXT_OBJECT_KEY, Book.class);
        var genre = library.findGenreById(id);
        if (genre.isEmpty()) {
            return "Genre with id: " + id + " does not exists";
        }
        bookObject.setGenre(genre.get());
        return getBookStatus();
    }

    @ShellMethod(value = "Set name to the book", key = {"book-name-set"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String bookSetName(String name) {
        var bookObject = shellContext.getObject(BOOK_CONTEXT_OBJECT_KEY, Book.class);
        bookObject.setName(name);
        return getBookStatus();
    }

    @ShellMethod(value = "Add author to the book", key = {"book-author-add"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String bookAddAuthor(Long id) {
        var author = library.findAuthorById(id);
        if (author.isEmpty()) {
            return "Author with id = " + id + " does not exists";
        }
        var bookObject = shellContext.getObject(BOOK_CONTEXT_OBJECT_KEY, Book.class);
        if (bookObject.getAuthors() == null) {
            bookObject.setAuthors(new ArrayList<>());
        }
        bookObject.getAuthors().add(author.get());
        return getBookStatus();
    }

    @ShellMethod(value = "New book status", key = {"book-status", "book-st"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String getBookStatus() {
        return shellContext.getObject(
                BOOK_CONTEXT_OBJECT_KEY, Book.class).toString() +
                System.lineSeparator() +
                NOT_SAVED_STATUS;
    }
    // endregion

    // region Common commands

    @ShellMethod(value = "Book create mode on", key = {"book-add", "book-new"}, group = ". Common commands")
    @ShellMethodAvailability("isRoot")
    public void addBookMode() {
        shellContext.addObject(BOOK_CONTEXT_OBJECT_KEY, new Book());
        shellContext.setContext(ShellContext.WorkingContext.CREATE_BOOK);
    }

    @ShellMethod(value = "Book edit mode on", key = {"book-edit"}, group = ". Common commands")
    @ShellMethodAvailability("isRoot")
    public void editBookMode(Long id) {
        var book = library.findBookById(id);
        shellContext.addObject(BOOK_CONTEXT_OBJECT_KEY, book.orElseThrow(() -> new RuntimeException("Book dont exists")));
        shellContext.setContext(ShellContext.WorkingContext.EDIT_BOOK);
    }

    @ShellMethod(value = "Root mode on", key = {"root", "..",}, group = ". Common commands")
    public void root() {
        shellContext.drop();
    }

    @ShellMethod(value = "Print all books", key = {"books"}, group = ". Common commands")
    public String getAllBooks() {
        return bookListDecorator.decorate(library.getAllBooks());
    }

    @ShellMethod(value = "Search book by id", key = {"book-by-id", "book"}, group = ". Common commands")
    public String findBookById(Long id) {
        var book = library.findBookById(id);
        return book.isEmpty() ? "null" : book.get().toString();
    }

    @ShellMethod(value = "Delete book by id", key = {"book-delete-id", "book-delete"}, group = ". Common commands")
    public String deleteBookById(Long id) {
        library.deleteBookById(id);
        return DELETED_STATUS;
    }

    @ShellMethod(value = "Print all genres", key = {"genres"}, group = ". Common commands")
    public String getAllGenres() {
        return genreListDecorator.decorate(library.getAllGenres());
    }

    @ShellMethod(value = "Search genre by id", key = {"genre-by-id", "genre"}, group = ". Common commands")
    public String findGenreById(Long id) {
        var genre = library.findGenreById(id);
        return genre.isEmpty() ? "null" : genre.get().toString();
    }

    @ShellMethod(value = "Print all authors", key = {"authors"}, group = ". Common commands")
    public String getAllAuthors() {
        return authorListDecorator.decorate(library.getAllAuthors());
    }

    @ShellMethod(value = "Search genre by id", key = {"author-by-id", "author"}, group = ". Common commands")
    public String findAuthorById(Long id) {
        var author = library.findAuthorById(id);
        return author.isEmpty() ? "null" : author.get().toString();
    }
    // endregion

    // region Availability

    public Availability isRoot() {
        return shellContext.getContext() == ShellContext.WorkingContext.ROOT ?
                Availability.available() :
                Availability.unavailable("Evaluable only in root mode");
    }

    public Availability isBookEditMode() {
        return (
                shellContext.getContext() == ShellContext.WorkingContext.CREATE_BOOK ||
                        shellContext.getContext() == ShellContext.WorkingContext.EDIT_BOOK
        ) ?
                Availability.available() :
                Availability.unavailable("You need enter to book create/edit mode");
    }
    // endregion
}