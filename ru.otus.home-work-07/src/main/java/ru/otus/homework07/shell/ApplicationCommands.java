package ru.otus.homework07.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.homework07.decorators.EntityListDecorator;
import ru.otus.homework07.dto.*;
import ru.otus.homework07.services.LibraryAuthorService;
import ru.otus.homework07.services.LibraryBookService;
import ru.otus.homework07.services.LibraryGenreService;

import java.util.HashSet;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private static final String BOOK_CONTEXT_OBJECT_KEY = "BOOK";
    private static final String NOT_SAVED_STATUS = "Not saved!";
    private static final String SAVED_STATUS = "Saved!";
    private static final String DELETED_STATUS = "Deleted!";

    private final ShellContext shellContext;
    private final LibraryBookService libraryBookService;
    private final LibraryAuthorService libraryAuthorService;
    private final LibraryGenreService libraryGenreService;
    private final EntityListDecorator<BookTitleDto> bookTitleDtoEntityListDecorator;
    private final EntityListDecorator<GenreDto> genreListDecorator;
    private final EntityListDecorator<AuthorDto> authorListDecorator;
    private final EntityListDecorator<BookCommentDto> commentListDecorator;

    // region Book creating/editing mode
    @ShellMethod(value = "Save book and go in to the root mode", key = {"book-save"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String saveBook() {
        var bookObject = shellContext.getObject(BOOK_CONTEXT_OBJECT_KEY, BookDto.class);
        var book = libraryBookService.save(bookObject);
        shellContext.drop();
        return book.toString() +
                System.lineSeparator() +
                SAVED_STATUS;
    }

    @ShellMethod(value = "Set genre to the book", key = {"book-genre-set"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String bookSetGenre(Long id) {
        var bookObject = shellContext.getObject(BOOK_CONTEXT_OBJECT_KEY, BookDto.class);
        var genre = libraryGenreService.findById(id);
        if (genre.isEmpty()) {
            return "Genre with id: " + id + " does not exists";
        }
        bookObject.setGenre(genre.get());
        return getBookStatus();
    }

    @ShellMethod(value = "Set name to the book", key = {"book-name-set"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String bookSetName(String name) {
        var bookObject = shellContext.getObject(BOOK_CONTEXT_OBJECT_KEY, BookDto.class);
        bookObject.setName(name);
        return getBookStatus();
    }

    @ShellMethod(value = "Add author to the book", key = {"book-author-add"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String bookAddAuthor(Long id) {
        var author = libraryAuthorService.findById(id);
        if (author.isEmpty()) {
            return "Author with id = " + id + " does not exists";
        }
        var bookObject = shellContext.getObject(BOOK_CONTEXT_OBJECT_KEY, BookDto.class);
        if (bookObject.getAuthors() == null) {
            bookObject.setAuthors(new HashSet<>());
        }
        bookObject.getAuthors().add(author.get());
        return getBookStatus();
    }

    @ShellMethod(value = "Add comment to the book", key = {"book-comment-add"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String bookAddComment(String comment) {
        var bookObject = shellContext.getObject(BOOK_CONTEXT_OBJECT_KEY, BookDto.class);
        if (bookObject.getBookComments() == null) {
            bookObject.setBookComments(new HashSet<>());
        }
        bookObject.getBookComments().add(new BookCommentDto().setText(comment));
        return getBookStatus();
    }

    @ShellMethod(value = "New book status", key = {"book-status", "book-st"}, group = ".. Book creating/editing mode")
    @ShellMethodAvailability("isBookEditMode")
    public String getBookStatus() {
        return shellContext.getObject(
                BOOK_CONTEXT_OBJECT_KEY, BookDto.class).toString() +
                System.lineSeparator() +
                NOT_SAVED_STATUS;
    }
    // endregion

    // region Common commands

    @ShellMethod(value = "Book create mode on", key = {"book-add", "book-new"}, group = ". Common commands")
    @ShellMethodAvailability("isRoot")
    public void addBookMode() {
        shellContext.addObject(BOOK_CONTEXT_OBJECT_KEY, new BookDto());
        shellContext.setContext(ShellContext.WorkingContext.CREATE_BOOK);
    }

    @ShellMethod(value = "Book edit mode on", key = {"book-edit"}, group = ". Common commands")
    @ShellMethodAvailability("isRoot")
    public void editBookMode(Long id) {
        var book = libraryBookService.findById(id);
        shellContext.addObject(BOOK_CONTEXT_OBJECT_KEY, book.orElseThrow(() -> new RuntimeException("Book dont exists")));
        shellContext.setContext(ShellContext.WorkingContext.EDIT_BOOK);
    }

    @ShellMethod(value = "Root mode on", key = {"root", "..",}, group = ". Common commands")
    public void root() {
        shellContext.drop();
    }

    @ShellMethod(value = "Print all books", key = {"books"}, group = ". Common commands")
    public String getAllBooks() {
        return bookTitleDtoEntityListDecorator.decorate(libraryBookService.getAll());
    }

    @ShellMethod(value = "Search book by id", key = {"book-by-id", "book"}, group = ". Common commands")
    public String findBookById(Long id) {
        var book = libraryBookService.findById(id);
        return book.isEmpty() ? "null" : book.get().toString();
    }

    @ShellMethod(value = "Delete book by id", key = {"book-delete-id", "book-delete"}, group = ". Common commands")
    public String deleteBookById(Long id) {
        libraryBookService.deleteById(id);
        return DELETED_STATUS;
    }

    @ShellMethod(value = "Print all genres", key = {"genres"}, group = ". Common commands")
    public String getAllGenres() {
        return genreListDecorator.decorate(libraryGenreService.getAll());
    }

    @ShellMethod(value = "Search genre by id", key = {"genre-by-id", "genre"}, group = ". Common commands")
    public String findGenreById(Long id) {
        var genre = libraryGenreService.findById(id);
        return genre.isEmpty() ? "null" : genre.get().toString();
    }

    @ShellMethod(value = "Print all authors", key = {"authors"}, group = ". Common commands")
    public String getAllAuthors() {
        return authorListDecorator.decorate(libraryAuthorService.getAll());
    }

    @ShellMethod(value = "Search genre by id", key = {"author-by-id", "author"}, group = ". Common commands")
    public String findAuthorById(Long id) {
        var author = libraryAuthorService.findById(id);
        return author.isEmpty() ? "null" : author.get().toString();
    }

    @ShellMethod(value = "Show all book's comments", key = {"book-comments", "comments"}, group = ". Common commands")
    public String findAllBookComments(Long id) {
        var bookComments = libraryBookService.getAllComments(id);
        return commentListDecorator.decorate(bookComments);
    }

    @ShellMethod(value = "Delete comment", key = {"book-comment-delete", "comment-delete"}, group = ". Common commands")
    public String deleteBookComment(Long id) {
        libraryBookService.deleteCommentById(id);
        return "Ok";
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