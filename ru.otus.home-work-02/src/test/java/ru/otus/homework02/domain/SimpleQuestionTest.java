package ru.otus.homework02.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SimpleQuestionTest {

    @Test
    void getTextTest() {
        var text = "Test question?";
        var q = new Question(text);

        Assertions.assertEquals(text, q.getText());
    }

    @Test
    void hasAnswersTest() {
        var q1 = new Question("Test", Arrays.asList("a1", "a2", "a3"), 0);
        Assertions.assertTrue(q1.hasAnswers());

        var q2 = new Question("Test");
        Assertions.assertFalse(q2.hasAnswers());
    }

    @Test
    void getAnswersTest() {
        var text = "Test question?";
        var answers = Arrays.asList("a1", "a2", "a3");
        var q = new Question(text, answers, 0);

        Assertions.assertEquals(answers, q.getAnswers());
    }
}