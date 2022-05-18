package ru.otus.homework02.service;

import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.QuestionBuilder;

import java.util.List;

public class CsvQuestionParser implements QuestionParser {

    private final static char CORRECT_ANSWER_MARKER = '*';
    private final static char CORRECT_ANSWER_MARKER_INDEX = 0;
    private final static int QUESTION_FIELD_INDEX = 0;
    private final static int ANSWERS_START_INDEX = 1;

    @Override
    public Question parse(List<String> fields) {
        var questionText = fields.get(QUESTION_FIELD_INDEX);
        if (questionText.isEmpty()) {
            throw new IncorrectCsvQuestionFileFormatException("Empty row");
        }

        var questionBuilder = new QuestionBuilder(questionText);

        for(int i = ANSWERS_START_INDEX; i < fields.size(); i++){
            if (fields.get(i).isEmpty()) {
                break;
            }

            questionBuilder.addAnswer(
                    getAnswerText(fields.get(i)),
                    isCorrectAnswer(fields.get(i))
            );
        }

        return questionBuilder.getQuestion();
    }

    private String getAnswerText(String text) {
        return isCorrectAnswer(text) ?
                text.substring(ANSWERS_START_INDEX) :
                text;
    }

    private boolean isCorrectAnswer(String text) {
        return text.charAt(CORRECT_ANSWER_MARKER_INDEX) == CORRECT_ANSWER_MARKER;
    }
}
