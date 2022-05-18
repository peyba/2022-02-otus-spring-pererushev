package ru.otus.homework02.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class TestResult {
    @Getter
    private final Student student;
    @Getter
    private final int successPercent;
    private final Map<Question, Answer> answers = new HashMap<>();

    public Iterable<Question> getQuestions() {
        return answers.keySet();
    }

    public Answer getAnswer(Question question) {
        return answers.get(question);
    }

    public void addAnswer(Question question, Answer answer) {
        answers.put(question, answer);
    }

    public boolean isCorrectAnswerForQuestion(Question question) {
        var studentAnswer = answers.get(question);
        var correctAnswers = question.getCorrectAnswer();

        return studentAnswer.equals(correctAnswers);
    }

    public int getCorrectAnswersCount() {
        int i = 0;
        for (var question : answers.keySet()) {
            if (isCorrectAnswerForQuestion(question)) {
                i++;
            }
        }
        return i;
    }

    public int getCorrectAnswersPercent() {
        if (answers.keySet().size() == 0) {
            return 100; // нет вопросов - нет проблем
        }

        var questionsCount = ((Integer) answers.keySet().size()).doubleValue();
        var correctAnswersCount = ((Integer) getCorrectAnswersCount()).doubleValue();

        return (int) ((100.00 / questionsCount) * correctAnswersCount);
    }

    public boolean isPassTest() {
        return getCorrectAnswersPercent() >= successPercent;
    }
}
