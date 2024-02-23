package com.adobe.orderapp.api;

import com.adobe.orderapp.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    Employee employee = new Employee();
    public EmployeeController() {
        employee.setId(1);
        employee.setTitle("Sr.Programmer");
        var personal = new HashMap<String, String>();
        personal.put("firstName", "Raj");
        personal.put("lastName", "Kumar");
        personal.put("phone"," 1234567890");
        employee.setPersonal(personal);

        var skills = new ArrayList<String>();
        skills.add("Spring Boot");
        skills.add("react");
        employee.setSkills(skills);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public Employee updateEmployee(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var target = patch.apply(mapper.readTree(mapper.writeValueAsString((employee))));
        return mapper.treeToValue(target, Employee.class);
        // employeeService.addEmployee(target);
    }
}
