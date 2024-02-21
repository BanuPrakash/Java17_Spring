package com.adobe.orderapp.dao;

import com.adobe.orderapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}

/*
    Spring DATA JPA is going to create @Repository class for this
    implementation with all pre-defined methods
 */