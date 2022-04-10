package ru.otus.homework02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework02.config.AppConfig;
import ru.otus.homework02.service.*;

@Configuration
@ComponentScan
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        var studentRegistrationService = context.getBean(StudentRegistrationService.class);
        var studentTestingService = context.getBean(StudentTestingService.class);

        var student = studentRegistrationService.registrationStudent();
        var testResult = studentTestingService.startTest(student);

        System.out.println(testResult);

        System.out.println("End.");
    }
}