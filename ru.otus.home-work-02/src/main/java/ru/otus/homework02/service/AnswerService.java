package ru.otus.homework02.service;

import lombok.NonNull;
import ru.otus.homework02.domain.Answer;

public interface AnswerService {
    Answer createAnswer(@NonNull String answerText, boolean isCorrect);
}
