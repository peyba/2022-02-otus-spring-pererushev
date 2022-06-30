package ru.otus.homework08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        final var context =
                new SpringApplication(Application.class).run(args);
    }
}