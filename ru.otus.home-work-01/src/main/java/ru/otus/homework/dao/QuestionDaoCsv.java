package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import lombok.SneakyThrows;
import ru.otus.homework.entity.Question;
import ru.otus.homework.entity.QuestionsCollection;
import ru.otus.homework.entity.SimpleQuestion;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoCsv implements QuestionDao {

    private final Resource csvFile;
    private final boolean hasHeader;

    public QuestionDaoCsv(Resource csvFile, boolean hasHeader) {
        this.csvFile = csvFile;
        this.hasHeader = hasHeader;
    }

    @SneakyThrows
    private QuestionsCollection parseCsv() {
        var questions = new QuestionsCollection();
        CSVReader reader = new CSVReader(new InputStreamReader(csvFile.getInputStream()));

        if (hasHeader) {
            reader.readNext();
        }

        String[] csvRow;
        while ((csvRow = reader.readNext()) != null) {
            questions.add(parseQuestion(csvRow));
        }

        return questions;
    }

    private Question parseQuestion(String[] csvRow) throws IncorrectCsvQuestionFileFormatException {
        var question = csvRow[0];
        if (question.isEmpty()) {
            throw new IncorrectCsvQuestionFileFormatException("Empty row");
        }

        List<String> answers = new ArrayList<>();
        for(int i = 1; i < csvRow.length; i++){
            if (!csvRow[i].isEmpty()){
                answers.add(csvRow[i]);
            }
        }

        if (answers.size() == 0) {
            return new SimpleQuestion(question, new String[0]);
        }
        else {
            return new SimpleQuestion(question, answers.toArray(String[]::new));
        }
    }

    public QuestionsCollection getAll() {
        return parseCsv();
    }
}
