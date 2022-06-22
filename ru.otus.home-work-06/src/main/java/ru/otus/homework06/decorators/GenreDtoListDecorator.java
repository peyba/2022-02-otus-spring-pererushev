package ru.otus.homework06.decorators;

import org.springframework.stereotype.Component;
import ru.otus.homework06.dto.GenreDto;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GenreDtoListDecorator extends AbstractEntityListDecorator<GenreDto> {
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
    protected Map<String, Object> mapEntity(GenreDto genreDto) {
        Map<String, Object> genrePrintMap = new HashMap<>();
        genrePrintMap.put("id", genreDto.getId());
        genrePrintMap.put("code", genreDto.getCode());
        genrePrintMap.put("name_eng", genreDto.getNameEng());
        genrePrintMap.put("name_rus", genreDto.getNameRus());
        return genrePrintMap;
    }
}
