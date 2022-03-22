package ru.otus.homework01;

import lombok.SneakyThrows;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework01.service.QuestionsPrinterService;
import ru.otus.homework01.service.SimpleQuestionsPrinterService;

public class App {
    @SneakyThrows
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionsPrinterService service = context.getBean(SimpleQuestionsPrinterService.class);

        service.print();
    }
}