package ru.otus.homework07.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.otus.homework07.domain.Author;
import ru.otus.homework07.domain.Genre;

import java.util.Set;

@Data
@Accessors(chain = true)
public class BookTitleDto {
    private Long id;
    private String name;
    private Genre genre;
    private Set<Author> authors;
}
