package ru.otus.homework07.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class BookTitleDto {
    private Long id;
    private String name;
    private GenreDto genre;
    private Set<AuthorDto> authors;
}