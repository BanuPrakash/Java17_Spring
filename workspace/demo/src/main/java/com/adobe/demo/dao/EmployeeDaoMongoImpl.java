package com.adobe.demo.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;



@Repository
public class EmployeeDaoMongoImpl implements  EmployeeDao{

    @Override
    public void addEmployee() {
        System.out.println("Mongo Store!!!");
    }
}
