package ru.otus.homework02.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {

    @Getter
    private final String text;
    private final Map<Answer, Boolean> answers = new HashMap<>();

    protected Question(@NonNull String text) {
        this.text = text;
    }

    public boolean hasAnswers() {
        return !answers.isEmpty();
    }

    protected void addAnswer(@NonNull String text, @NonNull Boolean isCorrect) {
        this.answers.put(new Answer(text), isCorrect);
    }

    public QuestionType getQuestionType() {
        if (answers.isEmpty()) {
            return QuestionType.FREE_ANSWER;
        }
        else if (answers.size() == 1) {
            return QuestionType.STRING_ANSWER;
        }
        else {
            return QuestionType.NUM_ANSWER;
        }
    }

    public List<Answer> getAnswers() {
        return new ArrayList<>(answers.keySet());
    }

    public Answer getCorrectAnswer() {
        if (getQuestionType() == QuestionType.STRING_ANSWER) {
            return answers.keySet().stream().findFirst().orElseThrow();
        }
        else {
            for (var answer : answers.keySet()) {
                if (answers.get(answer)) {
                    return answer;
                }
            }
        }
        return null;
    }
}