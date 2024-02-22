package com.adobe.orderapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private  int id;
    private  String title;
    Map<String,String> personal = new HashMap<>();
    List<String> skills = new ArrayList<>(); // 5 skills are there, need to add new skill in 2 position
}
// PUT Mapping -- send new istance of Employee to replace old one