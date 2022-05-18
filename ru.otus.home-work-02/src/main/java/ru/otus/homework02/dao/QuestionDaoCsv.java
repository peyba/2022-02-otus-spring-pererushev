package ru.otus.homework02.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.core.io.Resource;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.service.QuestionParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDaoCsv implements QuestionDao {

    private static final String FILE_NOT_EXISTS_ERROR = "Error! File does not exist. Filename: ";
    private static final String READING_FILE_ERROR = "Reading file error: ";

    private final Resource resource;
    private final QuestionParser parser;
    private final boolean hasHeader;

    public QuestionDaoCsv(Resource resource, QuestionParser parser, boolean hasHeader) {
        this.resource = resource;
        this.parser = parser;
        this.hasHeader = hasHeader;
    }

    public List<Question> getAll() {
        var questions = new ArrayList<Question>();
        CSVReader reader;
        try {
            reader = new CSVReader(new InputStreamReader(resource.getInputStream()));
        } catch (IOException e) {
            throw new CannotGetQuestionsException(FILE_NOT_EXISTS_ERROR + resource.getFilename());
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
            throw new CannotGetQuestionsException(READING_FILE_ERROR + e.getMessage());
        }
        return questions;
    }
}