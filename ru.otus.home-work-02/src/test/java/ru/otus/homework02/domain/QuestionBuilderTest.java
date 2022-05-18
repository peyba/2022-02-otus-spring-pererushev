package ru.otus.homework02.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionBuilderTest {

    @Test
    void addAnswerTest() {
        var qb = new QuestionBuilder("text");
        qb.addAnswer("a0", false);
        qb.addAnswer("a1", true);
        qb.addAnswer("a2", false);
        qb.addAnswer("a3", false);

        Assertions.assertEquals(qb.getQuestion().getAnswers().size(), 4);
    }

    @Test
    void getCorrectQuestionTest() {
        var qb = new QuestionBuilder("text");
        qb.addAnswer("a0", false);
        qb.addAnswer("a1", true);
        qb.addAnswer("a2", false);
        qb.addAnswer("a3", false);

        Assertions.assertNotNull(qb.getQuestion());
    }

    @Test
    void getIncorrectQuestionTest1() {
        var qb = new QuestionBuilder("text");
        qb.addAnswer("a0", false);
        qb.addAnswer("a1", false);
        qb.addAnswer("a2", false);
        qb.addAnswer("a3", false);

        Assertions.assertThrows(CreatingQuestionException.class, qb::getQuestion);
    }

    @Test
    void getIncorrectQuestionTest2() {
        var qb = new QuestionBuilder("text");
        qb.addAnswer("a0", true);
        qb.addAnswer("a2", false);
        qb.addAnswer("a3", false);

        Assertions.assertThrows(CreatingQuestionException.class, () -> qb.addAnswer("a3", true));
    }
}