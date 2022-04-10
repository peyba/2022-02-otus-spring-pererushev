package ru.otus.homework02.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
// TODO: нужен ли? подумать над системой хранения правильных ответов
public class Answer {
    private final String text;
    private final boolean isCorrect;
}
