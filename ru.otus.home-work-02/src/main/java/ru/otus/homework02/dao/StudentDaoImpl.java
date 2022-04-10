package ru.otus.homework02.dao;

import ru.otus.homework02.domain.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentDaoImpl implements StudentDao {

    private final Map<String, Student> students;

    public StudentDaoImpl() {
        this.students = new HashMap<>();
    }

    @Override
    public Student getByFirstName(String firstName) {
        return students.get(firstName);
    }

    @Override
    public Student save(String firstName, String secondName) {
        var newStudent = new Student(firstName, secondName);
        students.put(newStudent.getFirstName(), newStudent);
        return newStudent;
    }
}
