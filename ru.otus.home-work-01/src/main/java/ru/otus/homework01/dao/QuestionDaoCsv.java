package ru.otus.homework01.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ru.otus.homework01.domain.QuestionsCollection;
import org.springframework.core.io.Resource;
import ru.otus.homework01.service.QuestionParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class QuestionDaoCsv implements QuestionDao {

    private final Resource resource;
    private final boolean hasHeader;
    private final QuestionParser parser;

    public QuestionDaoCsv(Resource resource, QuestionParser parser, boolean hasHeader) {
        this.resource = resource;
        this.parser = parser;
        this.hasHeader = hasHeader;
    }

    public QuestionsCollection getAll() {
        var questions = new QuestionsCollection();
        CSVReader reader;
        try {
            reader = new CSVReader(new InputStreamReader(resource.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            if (hasHeader) {
                reader.readNext();
            }

            String[] csvRow;
            while ((csvRow = reader.readNext()) != null) {
                questions.add(parser.parse(Arrays.asList(csvRow)));
            }
        }
        catch (CsvValidationException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return questions;
    }
}
