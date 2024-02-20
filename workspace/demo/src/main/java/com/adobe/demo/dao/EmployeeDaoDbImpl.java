package com.adobe.demo.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnMissingBean(EmployeeDaoMongoImpl.class)
public class EmployeeDaoDbImpl implements  EmployeeDao{

    @Override
    public void addEmployee() {
        System.out.println("Stored in RDBMS!!!");
    }
}
