package ru.otus.homework02.service;

import lombok.NonNull;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.QuestionType;

import java.util.List;

public interface QuestionService {
    QuestionType getQuestionType(Question question);
    Answer getCorrectAnswer(Question question);
    Question createQuestion(@NonNull String text, List<Answer> answers);
    boolean hasAnswers(Question question);
}
