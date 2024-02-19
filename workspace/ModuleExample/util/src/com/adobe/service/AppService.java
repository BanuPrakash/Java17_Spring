package com.adobe.service;

import com.adobe.repo.EmployeeRepo;

import java.util.logging.Logger;

public class AppService {
    EmployeeRepo repo = new EmployeeRepo();
    Logger logger = Logger.getLogger(AppService.class.getName());
    public void insert(){
        logger.info("Employee ADDED");
        repo.addEmployee();
    }
}
