package com.example.tdstudent.validator;

import com.example.tdstudent.model.Student;
import com.example.tdstudent.exception.BadRequestException;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StudentValidator {

    public void validate(List<Student> students) {
        for (Student student : students) {
            if (student.getReference() == null || student.getReference().isBlank()) {
                throw new BadRequestException("Student.reference cannot be null or empty");
            }
            if (student.getFirstName() == null || student.getFirstName().isBlank()) {
                throw new BadRequestException("Student.firstName cannot be null or empty");
            }
            if (student.getLastName() == null || student.getLastName().isBlank()) {
                throw new BadRequestException("Student.lastName cannot be null or empty");
            }
        }
    }
}