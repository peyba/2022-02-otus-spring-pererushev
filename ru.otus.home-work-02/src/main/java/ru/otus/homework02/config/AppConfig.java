package ru.otus.homework02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.homework02.dao.QuestionDao;
import ru.otus.homework02.dao.QuestionDaoCsv;
import ru.otus.homework02.dao.StudentDao;
import ru.otus.homework02.dao.StudentDaoImpl;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.TestResult;
import ru.otus.homework02.service.*;
import ru.otus.homework02.service.write.*;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public Resource classPathResource() {
        return new ClassPathResource("questions.csv");
    }

    @Bean
    public QuestionParser csvQuestionParser() {
        return new CsvQuestionParser();
    }

    @Bean
    @Autowired
    public QuestionDao questionDaoCsv(
            Resource resource,
            QuestionParser questionParser,
            @Value("${testing.has-header}") Boolean hasHeader) {
        return new QuestionDaoCsv(resource, questionParser, hasHeader);
    }

    @Bean
    public StudentDao studentDao() {
        return new StudentDaoImpl();
    }

    @Bean
    public StudentRegistrationService studentRegistrationService(StudentDao studentDao, IOService ioService) {
        return new StudentRegistrationServiceImpl(studentDao, ioService);
    }

    @Bean
    public WriteAnswerService writeAnswerService() {
        return new WriteAnswerServiceImpl();
    }

    @Bean
    public WriteQuestionForQuizService writeQuestionForQuizService(WriteAnswerService writeAnswerService) {
        return new WriteQuestionForQuizService(writeAnswerService);
    }

    @Bean
    public IOService consoleIOService() {
        return new ConsoleIOService();
    }

    @Bean
    public StudentTestingService studentTestingService(QuestionDao questionDao, IOService ioService, WriteQuestionForQuizService writeQuestionService) {
        return new StudentTestingServiceImpl(questionDao, ioService, writeQuestionService);
    }

    @Bean
    public WriteQuestionCorrectAnswersService writeQuestionCorrectAnswersService(WriteService<Answer> answerWriteService) {
        return new WriteQuestionCorrectAnswersService(answerWriteService);
    }

    @Bean
    public WriteService<TestResult> writeTestResultService(WriteQuestionCorrectAnswersService questionWriteService, WriteService<Answer> answerWriteService) {
        return new WriteTestResultService(questionWriteService, answerWriteService);
    }

    @Bean
    public QuizService quizService(StudentRegistrationService studentRegistrationService, StudentTestingService studentTestingService, WriteService<TestResult> writeService, IOService ioService) {
        return new QuizServiceImpl(studentRegistrationService, studentTestingService, writeService, ioService);
    }
}