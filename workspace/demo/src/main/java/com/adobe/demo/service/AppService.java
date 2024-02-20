package com.adobe.demo.service;

import com.adobe.demo.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    @Autowired
    private EmployeeDao employeeDao; // autowire by type

    public void insert() {
        employeeDao.addEmployee();
    }
}
