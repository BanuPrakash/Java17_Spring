package com.adobe.orderapp.dao;

import com.adobe.orderapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    // select * from products where qty = ?
    List<Product> findByQuantity(int number);

    // select * from customers where firstName = ? and lastName = ?
    // List<Customer> findByFirstNameAndLastName(String fn, String ln);

//    @Query(value = "select * from products where price >= :l and price <= :h", nativeQuery = true)
    @Query("from Product where price >= :l and price <= :h")
    List<Product> getProductsByRange(@Param("l") double low, @Param("h")  double high);

    @Modifying
    @Query("update Product set price = :pr where id = :id")
    void updateProduct(@Param("id") int id, @Param("pr") double price);
}

/*
    Spring DATA JPA is going to create @Repository class for this
    implementation with all pre-defined methods
 */