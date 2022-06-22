package ru.otus.homework06.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GenreDto {
    private Long id;
    private String code;
    private String nameEng;
    private String nameRus;
}
