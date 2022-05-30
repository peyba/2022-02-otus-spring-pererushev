package ru.otus.homework05.decorators;

import org.springframework.stereotype.Component;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Genre;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookListDecorator extends AbstractEntityListDecorator<Book> {
    @Override
    protected Map<String, Integer> columns() {
        Map<String, Integer> columns = new LinkedHashMap<>();
        columns.put("id", 4);
        columns.put("name", 25);
        columns.put("genre", 25);
        columns.put("author", 50);
        return columns;
    }

    @Override
    protected Map<String, Object> mapEntity(Book book) {
        Map<String, Object> booksPrintMap = new HashMap<>();
        booksPrintMap.put("id", book.getId());
        booksPrintMap.put("name", book.getName());
        booksPrintMap.put("genre", getGenreString(book.getGenre()));
        booksPrintMap.put("author", getAuthorsString(book.getAuthors()));
        return booksPrintMap;
    }

    private String getAuthorsString(List<Author> authors) {
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