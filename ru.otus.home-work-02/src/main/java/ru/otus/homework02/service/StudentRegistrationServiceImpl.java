package ru.otus.homework02.service;

import ru.otus.homework02.dao.StudentDao;
import ru.otus.homework02.domain.Student;

import java.util.Scanner;

public class StudentRegistrationServiceImpl implements StudentRegistrationService {

    private final StudentDao studentDao;

    public StudentRegistrationServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student registrationStudent() {
        System.out.println("Hello!");
        var firstName = askQuestion("Enter your first name, please:");
        var secondName = askQuestion("Enter your second name, please:");

        return studentDao.save(firstName, secondName);
    }

    public String askQuestion(String question) {
        String answer = "";
        while (answer.isEmpty()) {
            System.out.println(question);
            answer = new Scanner(System.in).nextLine();
        }

        return answer;
    }
}
