package com.example.studentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private  final StudentRepo repo;

    public Student saveStudent(Student s) {
        return repo.save(s);
    }

    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    public List<Student> getForSchool(Integer schoolId) {
        return  repo.findAllBySchoolId(schoolId);
    }
}
