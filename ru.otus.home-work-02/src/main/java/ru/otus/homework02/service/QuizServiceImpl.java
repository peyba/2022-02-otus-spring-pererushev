package ru.otus.homework02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.otus.homework02.domain.TestResult;
import ru.otus.homework02.service.write.WriteService;

@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    @Value("${testing.success-percent}")
    private int successPercent;

    private final StudentRegistrationService studentRegistrationService;
    private final StudentTestingService studentTestingService;
    private final WriteService<TestResult> writeService;
    private final IOService ioService;

    @Override
    public void run() {
        var student = studentRegistrationService.registrationStudent();
        var testResult = studentTestingService.startTest(student, successPercent);
        ioService.writeLine(writeService.write(testResult));
    }
}