package ru.otus.homework09.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.homework09.dto.BookCommentDto;
import ru.otus.homework09.dto.BookDto;
import ru.otus.homework09.services.AuthorLibraryService;
import ru.otus.homework09.services.BookLibraryService;
import ru.otus.homework09.services.GenreLibraryService;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class BookLibraryController {

    private final BookLibraryService bookLibraryService;
    private final GenreLibraryService genreLibraryService;
    private final AuthorLibraryService authorLibraryService;

    @GetMapping("/")
    public String listPage(Model model) {
        var books = bookLibraryService.getAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") Long id, Model model) {
        var book = bookLibraryService.findById(id).orElseThrow(NotFoundException::new);
        var genres = genreLibraryService.getAll();
        var authors = authorLibraryService.getAll();
        model
                .addAttribute("book", book)
                .addAttribute("genres", genres)
                .addAttribute("authors", authors);
        return "book_edit";
    }

    @GetMapping("/book/add")
    public String addPage(Model model) {
        var genres = genreLibraryService.getAll();
        var authors = authorLibraryService.getAll();
        model
                .addAttribute("genres", genres)
                .addAttribute("authors", authors);
        return "book_add";
    }

    @PostMapping("/book/add")
    public String saveBook(
            @RequestParam("name") String name,
            @RequestParam("genre") Long genreId,
            @RequestParam("authors") String authors
    ) {
        var book = new BookDto();

        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        book.setName(name);

        if (authors.isEmpty()) {
            book.setAuthors(Set.of());
        } else {
            List<Long> authorsIdList = new ArrayList<>();
            List.of(authors.split(",")).forEach(a -> authorsIdList.add(Long.parseLong(a)));
            book.setAuthors(Set.copyOf(authorLibraryService.findAllById(authorsIdList)));
        }

        var genre = genreLibraryService.findById(genreId);
        if (genre.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        book.setGenre(genre.get());

        var savedBook = bookLibraryService.save(book);

        return String.format("redirect:/book/view?id=%s", savedBook.getId());
    }

    @GetMapping("/book/view")
    public String infoPage(@RequestParam("id") Long id, Model model) {
        var book = bookLibraryService.findById(id).orElseThrow(NotFoundException::new);
        var genres = genreLibraryService.getAll();
        var authors = authorLibraryService.getAll();
        model.addAttribute("book", book);
        return "book_view";
    }

    @PostMapping("/book/add_comment")
    public String addComment(
            @RequestParam("book_id") Long bookId,
            @RequestParam("comment") String comment
    ) {
        if (comment.isEmpty()) {
            return String.format("redirect:/book/view?id=%s", bookId);
        }

        var book = bookLibraryService.findById(bookId);
        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        book.get().getBookComments().add(new BookCommentDto().setText(comment));

        bookLibraryService.save(book.get());

        return String.format("redirect:/book/view?id=%s", bookId);
    }

    @PostMapping("/book/edit")
    public String saveBook(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("genre") Long genreId,
            @RequestParam("authors") String authors
    ) {
        var book = bookLibraryService.findById(id);
        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        book.get().setName(name);

        if (authors.isEmpty()) {
            book.get().setAuthors(Set.of());
        } else {
            List<Long> authorsIdList = new ArrayList<>();
            List.of(authors.split(",")).forEach(a -> authorsIdList.add(Long.parseLong(a)));
            book.get().setAuthors(Set.copyOf(authorLibraryService.findAllById(authorsIdList)));
        }

        var genre = genreLibraryService.findById(genreId);
        if (genre.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        book.get().setGenre(genre.get());

        bookLibraryService.save(book.get());

        return String.format("redirect:/book/view?id=%s", book.get().getId());
    }

    @GetMapping("/book/delete")
    public String deleteBook(@RequestParam("id") Long id) {
        var book = bookLibraryService.findById(id).orElseThrow(NotFoundException::new);
        bookLibraryService.deleteById(id);
        return "redirect:/";
    }
}