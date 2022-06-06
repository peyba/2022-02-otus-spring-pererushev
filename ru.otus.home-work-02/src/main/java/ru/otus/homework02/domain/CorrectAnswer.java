package ru.otus.homework02.domain;

import lombok.NonNull;

public class CorrectAnswer extends Answer {
    public CorrectAnswer(@NonNull String text) {
        super(text);
    }
}