package ru.otus.homework06.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.otus.homework06.domain.Author;
import ru.otus.homework06.domain.Genre;

import java.util.Set;

@Data
@Accessors(chain = true)
public class BookTitleDto {
    private Long id;
    private String name;
    private Genre genre;
    private Set<Author> authors;
}
