package ru.otus.homework02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework02.dao.QuestionDao;
import ru.otus.homework02.dao.QuestionDaoCsv;
import ru.otus.homework02.dao.StudentDao;
import ru.otus.homework02.dao.StudentDaoImpl;
import ru.otus.homework02.service.*;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public QuestionParser csvQuestionParser() {
        return new CsvQuestionParser();
    }

    @Bean
    @Autowired
    public QuestionDao questionDaoCsv(
            @Value("${testing.questions-file-path}") String filePath,
            QuestionParser questionParser,
            @Value("#{new Boolean('${testing.has-header}')}") Boolean hasHeader) {
        return new QuestionDaoCsv(filePath, questionParser, hasHeader);
    }

    @Bean
    public QuestionsPrinterService questionsPrinterService(QuestionDao questionDaoCsv) {
        return new SimpleQuestionsPrinterService(questionDaoCsv);
    }

    @Bean
    public StudentDao studentDao() {
        return new StudentDaoImpl();
    }

    @Bean
    public StudentRegistrationService studentRegistrationService(StudentDao studentDao) {
        return new StudentRegistrationServiceImpl(studentDao);
    }

    @Bean
    public StudentTestingService studentTestingService(QuestionDao questionDao) {
        return new StudentTestingServiceImpl(questionDao);
    }
}
