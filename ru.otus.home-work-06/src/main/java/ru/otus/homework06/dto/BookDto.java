package ru.otus.homework06.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class BookDto {
    private Long id;
    private String name;
    private GenreDto genre;
    private Set<AuthorDto> authors;
    private Set<BookCommentDto> bookComments;
}
