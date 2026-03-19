package com.example.tdstudent.controller;

import com.example.tdstudent.model.Student;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentControllerTD2 {

    // Stockage en mémoire vive
    private List<Student> students = new ArrayList<>();

    // A) GET /welcome avec @RequestParam
    @GetMapping("/welcome")
    public String welcome(@RequestParam(name = "name", required = false) String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Veuillez fournir un paramètre 'name'";
        }
        return "Welcome " + name;
    }

    // B) POST /students avec @RequestBody
    @PostMapping("/students")
    public String addStudents(@RequestBody List<Student> newStudents) {
        // Ajouter les nouveaux étudiants
        students.addAll(newStudents);

        // Retourner les noms
        if (students.isEmpty()) {
            return "Aucun étudiant enregistré";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < students.size(); i++) {
            if (i > 0)
                result.append(", ");
            result.append(students.get(i).getFullName());
        }
        return result.toString();
    }

    // C) GET /students avec entête Accept
    @GetMapping("/students")
    public String getStudents(@RequestHeader(value = "Accept", required = false) String acceptHeader) {
        if (acceptHeader == null) {
            return "L'entête Accept est requise";
        }

        if ("text/plain".equals(acceptHeader)) {
            if (students.isEmpty()) {
                return "Aucun étudiant enregistré";
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < students.size(); i++) {
                if (i > 0)
                    result.append(", ");
                result.append(students.get(i).getFullName());
            }
            return result.toString();
        } else {
            return "Format non supporté. Utilisez text/plain";
        }
    }
}