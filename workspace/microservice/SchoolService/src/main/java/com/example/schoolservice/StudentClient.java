package com.example.schoolservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="students", url="http://localhost:8090/api/students")
public interface StudentClient {
    @GetMapping("/school/{id}")
    public List<Student> bySchool(@PathVariable("id") Integer id);
}
