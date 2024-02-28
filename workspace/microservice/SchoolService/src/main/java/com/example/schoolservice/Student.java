package com.example.schoolservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO and not entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    String firstName;
    String lastName;
    String email;
}
