package com.example.schoolservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schools")
@RequiredArgsConstructor
public class SchoolController {
    final SchoolService service;

    @PostMapping
    public School save(@RequestBody School s) {
        return  service.save(s);
    }

    @GetMapping("/{id}")
    public SchoolResponse bySchool(@PathVariable("id") int id) {
        return service.getSchoolWithStudents(id);
    }
}
