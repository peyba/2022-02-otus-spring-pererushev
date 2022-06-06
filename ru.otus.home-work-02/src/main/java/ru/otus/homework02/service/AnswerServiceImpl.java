package ru.otus.homework02.service;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.CorrectAnswer;

@Component
public class AnswerServiceImpl implements AnswerService {
    @Override
    public Answer createAnswer(@NonNull String answerText, boolean isCorrect) {
        return isCorrect ? new CorrectAnswer(answerText) : new Answer(answerText);
    }
}
