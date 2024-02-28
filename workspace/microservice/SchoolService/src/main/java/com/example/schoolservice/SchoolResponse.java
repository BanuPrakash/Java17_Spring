package com.example.schoolservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolResponse {
    String name; // name of School
    List<Student> students; // thro microservice Student Microservice
}
