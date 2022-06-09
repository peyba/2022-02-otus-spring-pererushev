package ru.otus.homework02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan("ru.otus.homework02")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public Resource classPathResource() {
        return new ClassPathResource("questions.csv");
    }

}