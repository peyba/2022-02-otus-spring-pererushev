package ru.otus.homework08.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Accessors(chain = true)
@Data
@Document("genres")
public class Genre {
    @Id
    private Long id;

    private String code;

    private String nameEng;

    private String nameRus;
}