package com.example.schoolservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {
    final SchoolDao schoolDao;
    final StudentClient client; // bean of Feign client is wired

    public School save(School s) {
        return  schoolDao.save(s);
    }

    public SchoolResponse getSchoolWithStudents(Integer id){
        var school = schoolDao.findById(id)
                .orElse(School.builder().
                name("NO SCHOOL").build());

        var students = client.bySchool(id); // Other MS
        return SchoolResponse.builder().
                name(school.getName())
                        .students(students)
                .build();
    }
}
