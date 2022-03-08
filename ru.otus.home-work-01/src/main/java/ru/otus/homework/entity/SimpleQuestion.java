package ru.otus.homework.entity;

import java.util.Arrays;
import java.util.List;

public class SimpleQuestion implements Question {

    private final String text;
    private final String[] answers;

    public SimpleQuestion(String text) {
        this.text = text;
        this.answers = new String[0];
    }

    public SimpleQuestion(String text, String[] answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public boolean hasAnswers() {
        return answers.length != 0;
    }

    public List<String> getAnswers() {
        return Arrays.asList(answers);
    }

    @Override
    public String toString() {
        StringBuilder questionString = new StringBuilder();
        questionString.append(text);

        if (hasAnswers()){
            questionString.append('\n');
            // Do string like "1) answer_1; 2) answer_2; ...etc"
            for (int i = 1; i <= answers.length; i++) {
                questionString
                        .append(i)
                        .append(") ")
                        .append(answers[i - 1])
                        .append('\n');
            }
        }
        else {
            questionString.append('\n');
        }

        return questionString.toString();
    }
}