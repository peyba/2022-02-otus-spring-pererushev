package ru.otus.homework02.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Getter
    private final String text;
    private final List<Answer> answers;

    public List<Answer> getAnswers() {
        return List.copyOf(answers);
    }
}