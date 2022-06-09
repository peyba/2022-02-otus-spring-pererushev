package ru.otus.homework06.decorators;

import org.springframework.stereotype.Component;
import ru.otus.homework06.domain.BookComment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class BookCommentListDecorator extends AbstractEntityListDecorator<BookComment> {
    @Override
    protected Map<String, Integer> columns() {
        Map<String, Integer> columns = new LinkedHashMap<>();
        columns.put("book_id", 4);
        columns.put("book_name", 20);
        columns.put("id", 4);
        columns.put("text", 30);
        return columns;
    }

    @Override
    protected Map<String, Object> mapEntity(BookComment comment) {
        Map<String, Object> booksPrintMap = new HashMap<>();
        booksPrintMap.put("book_id", comment.getBook().getId());
        booksPrintMap.put("book_name", comment.getBook().getName());
        booksPrintMap.put("id", comment.getId());
        booksPrintMap.put("text", comment.getText());
        return booksPrintMap;
    }
}