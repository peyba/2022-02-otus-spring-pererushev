package ru.otus.homework08.decorators;

import org.springframework.stereotype.Component;
import ru.otus.homework08.dto.BookCommentDto;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class BookCommentDtoListDecorator extends AbstractEntityListDecorator<BookCommentDto> {
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
    protected Map<String, Object> mapEntity(BookCommentDto bookCommentDto) {
        Map<String, Object> booksPrintMap = new HashMap<>();
        booksPrintMap.put("id", bookCommentDto.getId());
        booksPrintMap.put("text", bookCommentDto.getText());
        return booksPrintMap;
    }
}