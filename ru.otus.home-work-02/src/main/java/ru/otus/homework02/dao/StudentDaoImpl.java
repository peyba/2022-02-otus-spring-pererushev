package ru.otus.homework02.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework02.domain.Student;

import java.util.HashMap;
import java.util.Map;

@Repository
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
