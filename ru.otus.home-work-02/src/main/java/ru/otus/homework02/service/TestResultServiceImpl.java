package ru.otus.homework02.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.TestResult;

@Component
public class TestResultServiceImpl implements TestResultService {

    @Getter
    private final int successPercent;
    private final QuestionService questionService;

    public TestResultServiceImpl(
            @Value("${testing.success-percent}") int successPercent,
            QuestionService questionService
    ) {
        this.successPercent = successPercent;
        this.questionService = questionService;
    }

    @Override
    public boolean isCorrectAnswerForQuestion(TestResult testResult, Question question) {
        var studentAnswer = testResult.getAnswer(question);
        var correctAnswers = questionService.getCorrectAnswer(question);

        return studentAnswer.equals(correctAnswers);
    }

    @Override
    public int getCorrectAnswersCount(TestResult testResult) {
        int i = 0;
        for (var question : testResult.getQuestions()) {
            if (isCorrectAnswerForQuestion(testResult, question)) {
                i++;
            }
        }
        return i;
    }

    @Override
    public int getCorrectAnswersPercent(TestResult testResult) {
        if (testResult.getQuestions().size() == 0) {
            return 100; // нет вопросов - нет проблем
        }

        var questionsCount = ((Integer) testResult.getQuestions().size()).doubleValue();
        var correctAnswersCount = ((Integer) getCorrectAnswersCount(testResult)).doubleValue();

        return (int) ((100.00 / questionsCount) * correctAnswersCount);
    }

    @Override
    public boolean isPassTest(TestResult testResult) {
        return getCorrectAnswersPercent(testResult) >= successPercent;
    }
}
