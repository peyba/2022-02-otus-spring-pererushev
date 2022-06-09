package ru.otus.homework06.decorators;

import org.springframework.stereotype.Component;
import ru.otus.homework06.domain.Author;
import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.Genre;

import java.util.*;

@Component
public class BookListDecorator extends AbstractEntityListDecorator<Book> {
    @Override
    protected Map<String, Integer> columns() {
        Map<String, Integer> columns = new LinkedHashMap<>();
        columns.put("id", 4);
        columns.put("name", 25);
        columns.put("genre", 25);
        columns.put("author", 50);
        columns.put("comments", 8);
        return columns;
    }

    @Override
    protected Map<String, Object> mapEntity(Book book) {
        Map<String, Object> booksPrintMap = new HashMap<>();
        booksPrintMap.put("id", book.getId());
        booksPrintMap.put("name", book.getName());
        booksPrintMap.put("genre", getGenreString(book.getGenre()));
        booksPrintMap.put("author", getAuthorsString(book.getAuthors()));
        booksPrintMap.put("comments", book.getBookComments().size());
        return booksPrintMap;
    }

    private String getAuthorsString(Collection<Author> authors) {
        var sb = new StringBuilder();
        for (var author : authors) {
            sb
                    .append(author.getId())
                    .append(" - ")
                    .append(author.getSecondName())
                    .append(" ")
                    .append(author.getFirstName())
                    .append("; ");
        }
        return sb.toString();
    }

    private String getGenreString(Genre genre) {
        return genre.getId() + " - " + genre.getNameRus();
    }
}