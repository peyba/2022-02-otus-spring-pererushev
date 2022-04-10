package ru.otus.homework02.dao;

import ru.otus.homework02.domain.Student;

public interface StudentDao {
    Student getByFirstName(String firstName);
    Student save(String firstName, String secondName);
}
