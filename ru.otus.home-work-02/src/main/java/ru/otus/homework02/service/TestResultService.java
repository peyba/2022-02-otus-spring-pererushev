package ru.otus.homework02.service;

import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.TestResult;

public interface TestResultService {

    boolean isCorrectAnswerForQuestion(TestResult testResult, Question question);

    int getCorrectAnswersCount(TestResult testResult);

    int getCorrectAnswersPercent(TestResult testResult);

    boolean isPassTest(TestResult testResult);
}
