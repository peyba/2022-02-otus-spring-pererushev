package ru.otus.homework06.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthorDto {
    private Long id;
    private String firstName;
    private String secondName;
}
