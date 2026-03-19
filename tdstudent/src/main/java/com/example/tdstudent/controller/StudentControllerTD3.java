package com.example.tdstudent.controller;

import com.example.tdstudent.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2") // Version 2 pour TD3
public class StudentControllerTD3 {

    private List<Student> students = new ArrayList<>();

    // a) GET /welcome avec gestion des codes HTTP
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(@RequestParam(name = "name", required = false) String name) {
        try {
            // Paramètre manquant -> 400 BAD REQUEST
            if (name == null || name.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", System.currentTimeMillis());
                response.put("status", 400);
                response.put("error", "Bad Request");
                response.put("message", "Le paramètre 'name' est requis");

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json")
                        .body(response);
            }

            // Succès -> 200 OK
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", System.currentTimeMillis());
            response.put("status", 200);
            response.put("message", "Welcome " + name);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .header("X-Custom-Header", "TD3-Welcome")
                    .body(response);

        } catch (Exception e) {
            // Erreur serveur -> 500
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", System.currentTimeMillis());
            response.put("status", 500);
            response.put("error", "Internal Server Error");
            response.put("message", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(response);
        }
    }

    // b) POST /students avec gestion des codes HTTP
    @PostMapping("/students")
    public ResponseEntity<?> addStudents(@RequestBody List<Student> newStudents) {
        try {
            // Validation des données
            if (newStudents == null || newStudents.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", System.currentTimeMillis());
                response.put("status", 400);
                response.put("error", "Bad Request");
                response.put("message", "La liste d'étudiants ne peut pas être vide");

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json")
                        .body(response);
            }

            // Validation de chaque étudiant
            for (Student student : newStudents) {
                if (student.getReference() == null || student.getReference().trim().isEmpty() ||
                        student.getFirstName() == null || student.getFirstName().trim().isEmpty() ||
                        student.getLastName() == null || student.getLastName().trim().isEmpty() ||
                        student.getAge() <= 0) {

                    Map<String, Object> response = new HashMap<>();
                    response.put("timestamp", System.currentTimeMillis());
                    response.put("status", 400);
                    response.put("error", "Bad Request");
                    response.put("message", "Données étudiant invalides");

                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .header("Content-Type", "application/json")
                            .body(response);
                }
            }

            // Ajout des étudiants
            students.addAll(newStudents);

            // Succès - 201 CREATED
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", System.currentTimeMillis());
            response.put("status", 201);
            response.put("message", "Étudiants ajoutés avec succès");
            response.put("total", students.size());
            response.put("students", students);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .header("X-Total-Count", String.valueOf(students.size()))
                    .body(response);

        } catch (Exception e) {
            // Erreur serveur - 500
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", System.currentTimeMillis());
            response.put("status", 500);
            response.put("error", "Internal Server Error");
            response.put("message", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(response);
        }
    }

    // c) GET /students avec gestion avancée
    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(value = "Accept", required = false) String acceptHeader) {
        try {
            // Vérification de l'entête Accept
            if (acceptHeader == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", System.currentTimeMillis());
                response.put("status", 400);
                response.put("error", "Bad Request");
                response.put("message", "L'entête Accept est requise");

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json")
                        .body(response);
            }

            // Format text/plain
            if ("text/plain".equals(acceptHeader)) {
                if (students.isEmpty()) {
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .header("Content-Type", "text/plain")
                            .body("Aucun étudiant enregistré");
                }

                StringBuilder result = new StringBuilder();
                for (int i = 0; i < students.size(); i++) {
                    if (i > 0)
                        result.append(", ");
                    result.append(students.get(i).getFullName());
                }

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type", "text/plain")
                        .header("X-Total-Count", String.valueOf(students.size()))
                        .body(result.toString());
            }

            // Format application/json
            else if ("application/json".equals(acceptHeader)) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .header("X-Total-Count", String.valueOf(students.size()))
                        .body(students);
            }

            // Format non supporté - 501 NOT IMPLEMENTED
            else {
                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", System.currentTimeMillis());
                response.put("status", 501);
                response.put("error", "Not Implemented");
                response.put("message", "Format non supporté");
                response.put("supported_formats", "text/plain, application/json");
                response.put("requested_format", acceptHeader);

                return ResponseEntity
                        .status(HttpStatus.NOT_IMPLEMENTED)
                        .header("Content-Type", "application/json")
                        .body(response);
            }

        } catch (Exception e) {
            // Erreur serveur - 500
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", System.currentTimeMillis());
            response.put("status", 500);
            response.put("error", "Internal Server Error");
            response.put("message", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(response);
        }
    }
}