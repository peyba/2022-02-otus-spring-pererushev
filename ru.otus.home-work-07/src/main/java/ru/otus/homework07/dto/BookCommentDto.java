package ru.otus.homework07.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookCommentDto {
    private Long id;
    private String text;
}