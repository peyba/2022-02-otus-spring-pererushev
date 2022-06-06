package ru.otus.homework05.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class Book {
    private Long id;
    private String name;
    private Genre genre;
    private List<Author> authors;
}
