package ru.otus.homework02.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework02.config.AppConfig;
import ru.otus.homework02.domain.QuestionType;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class })
class QuestionServiceImplTest {

    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;

    @Test
    void hasNotAnswersTest() {
        var q = questionService.createQuestion("test", List.of());
        Assertions.assertFalse(questionService.hasAnswers(q));
    }

    @Test
    void hasAnswersTest() {
        var q = questionService.createQuestion(
                "test",
                List.of(answerService.createAnswer("a1", true))
        );
        Assertions.assertTrue(questionService.hasAnswers(q));
    }

    @Test
    void addAnswerTest() {
        var q = questionService.createQuestion(
                "test",
                List.of(
                        answerService.createAnswer("a1", true),
                        answerService.createAnswer("a2", false)
                )
        );
        Assertions.assertEquals(2, q.getAnswers().size());
    }

    @Test
    void getQuestionTypeTestNumAnswer() {
        var q = questionService.createQuestion(
                "test",
                List.of(
                        answerService.createAnswer("a1", true),
                        answerService.createAnswer("a2", false)
                )
        );

        Assertions.assertEquals(QuestionType.NUM_ANSWER, questionService.getQuestionType(q));
    }

    @Test
    void getQuestionTypeTestStringAnswer() {
        var q = questionService.createQuestion(
                "test",
                List.of(answerService.createAnswer("a1", true))
        );

        Assertions.assertEquals(QuestionType.STRING_ANSWER, questionService.getQuestionType(q));
    }

    @Test
    void getQuestionTypeTestFreeAnswer() {
        var q = questionService.createQuestion("test", List.of());

        Assertions.assertEquals(QuestionType.FREE_ANSWER, questionService.getQuestionType(q));
    }

    @Test
    void getAnswersTest() {
        var q = questionService.createQuestion(
                "test",
                List.of(answerService.createAnswer("a1", true))
        );

        Assertions.assertEquals("a1", q.getAnswers().get(0).getText());
    }

    @Test
    void getCorrectAnswersTest() {
        var q = questionService.createQuestion(
                "test",
                List.of(
                        answerService.createAnswer("a1", true),
                        answerService.createAnswer("a2", false),
                        answerService.createAnswer("a3", false)
                )
        );

        Assertions.assertEquals("a1", questionService.getCorrectAnswer(q).getText());
    }
}