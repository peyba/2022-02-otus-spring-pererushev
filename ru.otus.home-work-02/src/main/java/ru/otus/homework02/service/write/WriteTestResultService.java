package ru.otus.homework02.service.write;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.TestResult;
import ru.otus.homework02.service.TestResultService;

@Component
public class WriteTestResultService implements WriteService<TestResult> {

    private static final String TEST_RESULT_SEPARATOR = "-----------------------------------------------------------------------" +
            System.lineSeparator() +
            "=============================== RESULT ================================" +
            System.lineSeparator() +
            "-----------------------------------------------------------------------";
    private static final String STUDENT_INTRO = "Test result for student ";
    private static final String STUDENT_ANSWER_INTRO = "Your answer is: ";
    private static final String CORRECT_ANSWERS_PERCENT = "Correct answers percent: ";
    private static final String RESULT_PART_2 = "";
    private static final String TEST_PASSED = "Test passed";
    private static final String TEST_FAILED = "Test failed";

    private final WriteService<Question> writeQuestionService;
    private final WriteService<Answer> answerWriteService;
    private final TestResultService testResultService;

    public WriteTestResultService(
            @Qualifier("writeQuestionCorrectAnswersService") WriteService<Question> writeQuestionService,
            @Qualifier("writeAnswerServiceImpl") WriteService<Answer> answerWriteService,
            TestResultService testResultService
    ) {
        this.writeQuestionService = writeQuestionService;
        this.answerWriteService = answerWriteService;
        this.testResultService = testResultService;
    }

    @Override
    public String write(TestResult testResult) {
        var result = new StringBuilder();

        result
                .append(TEST_RESULT_SEPARATOR)
                .append(System.lineSeparator())
                .append(STUDENT_INTRO)
                .append(testResult.getStudent().getFullName())
                .append(System.lineSeparator());

        for (var question : testResult.getQuestions()) {
            result
                    .append(writeQuestionService.write(question))
                    .append(STUDENT_ANSWER_INTRO)
                    .append(answerWriteService.write(testResult.getAnswer(question)))
                    .append(getAnswerCorrectnessText(testResult, question))
                    .append(System.lineSeparator());

        }

        result
                .append(System.lineSeparator())
                .append(CORRECT_ANSWERS_PERCENT)
                .append(testResultService.getCorrectAnswersPercent(testResult))
                .append(System.lineSeparator())
                .append(getTestResultText(testResult));

        return result.toString();
    }

    private String getAnswerCorrectnessText(TestResult testResult, Question question) {
        return "(" +
                (testResultService.isCorrectAnswerForQuestion(testResult, question) ? "+" : "-") +
                ")";
    }

    private String getTestResultText(TestResult testResult) {
        return testResultService.isPassTest(testResult) ? TEST_PASSED : TEST_FAILED;
    }
}