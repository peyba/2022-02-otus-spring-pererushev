package ru.otus.homework08.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Accessors(chain = true)
@Data
@Document("authors")
public class Author {
    @Id
    private Long id;

    private String firstName;

    private String secondName;
}
