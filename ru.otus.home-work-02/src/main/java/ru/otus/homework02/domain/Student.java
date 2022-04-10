package ru.otus.homework02.domain;

import lombok.Getter;

@Getter
public class Student {
    private final String firstName;
    private final String secondName;

    public Student(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFullName() {
        return firstName + " " + secondName;
    }
}