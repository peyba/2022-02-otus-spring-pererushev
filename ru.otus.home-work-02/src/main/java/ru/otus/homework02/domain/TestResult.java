package ru.otus.homework02.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TestResult {
    @Getter
    private final Student student;
    private final Map<Question, Answer> answers = new HashMap<>();

    public List<Question> getQuestions() {
        return List.copyOf(answers.keySet());
    }

    public Answer getAnswer(Question question) {
        return answers.get(question);
    }

    public void addAnswer(Question question, Answer answer) {
        answers.put(question, answer);
    }
}
