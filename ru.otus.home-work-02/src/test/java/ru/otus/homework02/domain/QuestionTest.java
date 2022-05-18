package ru.otus.homework02.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QuestionTest {

    @Test
    void hasNotAnswersTest() {
        var qb = new QuestionBuilder("test");
        Assertions.assertFalse(qb.getQuestion().hasAnswers());
    }

    @Test
    void hasAnswersTest() {
        var qb = new QuestionBuilder("test");
        qb.addAnswer("a1", true);
        Assertions.assertTrue(qb.getQuestion().hasAnswers());
    }

    @Test
    void addAnswerTest() {
        var qb = new QuestionBuilder("test");
        qb.addAnswer("a1", false);
        qb.addAnswer("a2", true);

        Assertions.assertEquals(2, qb.getQuestion().getAnswers().size());
    }

    @Test
    void getQuestionTypeTest() {
        var qb = new QuestionBuilder("test");
        Assertions.assertEquals(QuestionType.FREE_ANSWER, qb.getQuestion().getQuestionType());

        qb.addAnswer("a1", false);
        Assertions.assertEquals(QuestionType.STRING_ANSWER, qb.getQuestion().getQuestionType());

        qb.addAnswer("a2", true);
        Assertions.assertEquals(QuestionType.NUM_ANSWER, qb.getQuestion().getQuestionType());
    }

    @Test
    void getAnswersTest() {
        var qb = new QuestionBuilder("test");
        qb.addAnswer("a1", true);

        Assertions.assertEquals("a1", qb.getQuestion().getAnswers().get(0).getText());
    }

    @Test
    void getCorrectAnswersTest() {
        var qb = new QuestionBuilder("test");
        qb.addAnswer("a0", false);
        qb.addAnswer("a1", true);
        qb.addAnswer("a2", false);

        Assertions.assertEquals("a1", qb.getQuestion().getCorrectAnswer().getText());
    }
}