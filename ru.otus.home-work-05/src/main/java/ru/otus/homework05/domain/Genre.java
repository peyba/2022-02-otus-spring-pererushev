package ru.otus.homework05.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Genre {
    private Long id;
    private String code;
    private String nameEng;
    private String nameRus;
}