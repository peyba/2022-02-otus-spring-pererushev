package ru.otus.homework02.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.service.QuestionParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDaoCsv implements QuestionDao {

    private final File file;
    private final boolean hasHeader;
    private final QuestionParser parser;

    public QuestionDaoCsv(String filePath, QuestionParser parser, boolean hasHeader) {
        this.file = new File(filePath);
        this.parser = parser;
        this.hasHeader = hasHeader;
    }

    public List<Question> getAll() {
        var questions = new ArrayList<Question>();
        CSVReader reader;
        try {
            reader = new CSVReader(new InputStreamReader(new FileInputStream(file)));
        } catch (IOException e) {
            System.out.printf("Error! File \"%s\" does not exist.\n", file.getAbsolutePath());
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
            // TODO: добавить пользовательское исключение
            e.printStackTrace();
            return null;
        }
        return questions;
    }
}
