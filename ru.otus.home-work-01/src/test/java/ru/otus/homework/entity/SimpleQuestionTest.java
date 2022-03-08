package ru.otus.homework.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleQuestionTest {

    @Test
    void getTextTest() {
        var text = "Test question?";
        var q = new SimpleQuestion(text);

        Assertions.assertEquals(text, q.getText());
    }

    @Test
    void hasAnswersTest() {
        var q1 = new SimpleQuestion("Test", new String[] { "a1", "a2", "a3" });
        Assertions.assertTrue(q1.hasAnswers());

        var q2 = new SimpleQuestion("Test");
        Assertions.assertFalse(q2.hasAnswers());
    }

    @Test
    void getAnswersTest() {
        var text = "Test question?";
        var answers = new String[] { "a1", "a2", "a3" };
        var q = new SimpleQuestion(text, answers);

        Assertions.assertArrayEquals(answers, q.getAnswers().toArray());
    }
}