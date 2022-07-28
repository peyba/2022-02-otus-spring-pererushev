package ru.otus.homework09.decorators;

import org.springframework.stereotype.Component;
import ru.otus.homework09.dto.AuthorDto;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AuthorDtoListDecorator extends AbstractEntityListDecorator<AuthorDto> {
    @Override
    protected Map<String, Integer> columns() {
        Map<String, Integer> columns = new LinkedHashMap<>();
        columns.put("id", 4);
        columns.put("first_name", 50);
        columns.put("second_name", 50);
        return columns;
    }

    @Override
    protected Map<String, Object> mapEntity(AuthorDto authorDto) {
        Map<String, Object> genrePrintMap = new HashMap<>();
        genrePrintMap.put("id", authorDto.getId());
        genrePrintMap.put("first_name", authorDto.getFirstName());
        genrePrintMap.put("second_name", authorDto.getSecondName());
        return genrePrintMap;
    }
}
