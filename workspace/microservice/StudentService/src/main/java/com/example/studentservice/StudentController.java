package com.example.studentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class StudentController {
    private final StudentService service;

    @PostMapping
    public Student save(@RequestBody Student s) {
        return service.saveStudent(s);
    }

    @GetMapping
    public List<Student> getStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/school/{id}")
    public List<Student> getStudentsForSchool(@PathVariable("id") Integer schoolId) {
        return service.getForSchool(schoolId);
    }

}
