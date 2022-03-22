package ru.otus.homework.service;

import ru.otus.homework.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class CsvQuestionParser implements QuestionParser {
    @Override
    public Question parse(List<String> fields) {
        var question = fields.get(0);
        if (question.isEmpty()) {
            throw new IncorrectCsvQuestionFileFormatException("Empty row");
        }

        List<String> answers = new ArrayList<>();
        for(int i = 1; i < fields.size(); i++){
            if (!fields.get(i).isEmpty()){
                answers.add(fields.get(i));
            }
        }

        if (answers.size() == 0) {
            return new Question(question, new ArrayList<>());
        }
        else {
            return new Question(question, answers);
        }
    }
}
