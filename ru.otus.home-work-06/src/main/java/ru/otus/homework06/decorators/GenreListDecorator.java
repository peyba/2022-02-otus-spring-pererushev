package ru.otus.homework06.decorators;

import org.springframework.stereotype.Component;
import ru.otus.homework06.domain.Genre;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GenreListDecorator extends AbstractEntityListDecorator<Genre> {
    @Override
    protected Map<String, Integer> columns() {
        Map<String, Integer> columns = new LinkedHashMap<>();
        columns.put("id", 4);
        columns.put("code", 20);
        columns.put("name_eng", 50);
        columns.put("name_rus", 50);
        return columns;
    }

    @Override
    protected Map<String, Object> mapEntity(Genre genre) {
        Map<String, Object> genrePrintMap = new HashMap<>();
        genrePrintMap.put("id", genre.getId());
        genrePrintMap.put("code", genre.getCode());
        genrePrintMap.put("name_eng", genre.getNameEng());
        genrePrintMap.put("name_rus", genre.getNameRus());
        return genrePrintMap;
    }
}
