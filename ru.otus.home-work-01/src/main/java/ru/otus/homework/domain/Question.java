package ru.otus.homework.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private final String text;
    private final List<String> answers;

    public Question(String text) {
        this.text = text;
        this.answers = new ArrayList<>();
    }

    public Question(String text, List<String> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public boolean hasAnswers() {
        return answers.size() != 0;
    }

    public List<String> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        StringBuilder questionString = new StringBuilder();
        questionString.append(text);

        if (hasAnswers()){
            questionString.append('\n');
            // Do string like "1) answer_1; 2) answer_2; ...etc"
            for (int i = 1; i <= answers.size(); i++) {
                questionString
                        .append(i)
                        .append(") ")
                        .append(answers.get(i - 1))
                        .append('\n');
            }
        }
        else {
            questionString.append('\n');
        }

        return questionString.toString();
    }
}