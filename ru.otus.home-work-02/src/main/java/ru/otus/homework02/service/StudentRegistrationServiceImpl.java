package ru.otus.homework02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework02.dao.StudentDao;
import ru.otus.homework02.domain.Student;

@RequiredArgsConstructor
@Component
public class StudentRegistrationServiceImpl implements StudentRegistrationService {
    
    private static final String GREETING = "Hello!";
    private static final String ASL_F_NAME = "Enter your first name, please:";
    private static final String ASL_S_NAME = "Enter your second name, please:";

    private final StudentDao studentDao;
    private final IOService ioService;

    @Override
    public Student registrationStudent() {
        ioService.writeLine(GREETING);
        var firstName = askQuestion(ASL_F_NAME);
        var secondName = askQuestion(ASL_S_NAME);

        return studentDao.save(firstName, secondName);
    }

    public String askQuestion(String question) {
        String answer = "";
        while (answer.isEmpty()) {
            ioService.writeLine(question);
            answer = ioService.readLine();
        }

        return answer;
    }
}