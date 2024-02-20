package com.adobe.demo.dao;

import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoDbImpl implements  EmployeeDao{

    @Override
    public void addEmployee() {
        System.out.println("Stored in RDBMS!!!");
    }
}
