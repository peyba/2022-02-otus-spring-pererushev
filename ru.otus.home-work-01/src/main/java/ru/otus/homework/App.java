package ru.otus.homework;

import lombok.SneakyThrows;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.service.QuestionsPrinterService;
import ru.otus.homework.service.SimpleQuestionsPrinterService;

public class App {
    @SneakyThrows
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionsPrinterService service = context.getBean(SimpleQuestionsPrinterService.class);

        service.print();
    }
}