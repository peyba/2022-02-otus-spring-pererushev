package ru.otus.homework06.decorators;

import org.springframework.stereotype.Component;
import ru.otus.homework06.domain.Author;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AuthorListDecorator extends AbstractEntityListDecorator<Author> {
    @Override
    protected Map<String, Integer> columns() {
        Map<String, Integer> columns = new LinkedHashMap<>();
        columns.put("id", 4);
        columns.put("first_name", 50);
        columns.put("second_name", 50);
        return columns;
    }

    @Override
    protected Map<String, Object> mapEntity(Author author) {
        Map<String, Object> genrePrintMap = new HashMap<>();
        genrePrintMap.put("id", author.getId());
        genrePrintMap.put("first_name", author.getFirstName());
        genrePrintMap.put("second_name", author.getSecondName());
        return genrePrintMap;
    }
}
