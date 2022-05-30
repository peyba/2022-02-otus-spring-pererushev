package ru.otus.homework05.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Author {
    private Long id;
    private String firstName;
    private String secondName;
}
