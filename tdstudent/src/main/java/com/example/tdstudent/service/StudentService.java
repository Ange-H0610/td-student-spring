package com.example.tdstudent.service;

import com.example.tdstudent.model.Student;
import com.example.tdstudent.validator.StudentValidator;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> studentsInMemory = new ArrayList<>();
    private final StudentValidator validator;

    public StudentService(StudentValidator validator) {
        this.validator = validator;
    }

    public List<Student> addStudents(List<Student> newStudents) {
        validator.validate(newStudents);
        studentsInMemory.addAll(newStudents);
        return studentsInMemory;
    }
}