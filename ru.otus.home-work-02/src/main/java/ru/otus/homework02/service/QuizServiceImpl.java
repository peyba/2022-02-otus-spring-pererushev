package ru.otus.homework02.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.TestResult;
import ru.otus.homework02.service.write.WriteService;

@Component
public class QuizServiceImpl implements QuizService {

    private final StudentRegistrationService studentRegistrationService;
    private final StudentTestingService studentTestingService;
    private final WriteService<TestResult> writeService;
    private final IOService ioService;

    public QuizServiceImpl(
            StudentRegistrationService studentRegistrationService,
            StudentTestingService studentTestingService,
            @Qualifier("writeTestResultService") WriteService<TestResult> writeService,
            IOService ioService) {
        this.studentRegistrationService = studentRegistrationService;
        this.studentTestingService = studentTestingService;
        this.writeService = writeService;
        this.ioService = ioService;
    }

    @Override
    public void run() {
        var student = studentRegistrationService.registrationStudent();
        var testResult = studentTestingService.startTest(student);
        ioService.writeLine(writeService.write(testResult));
    }
}