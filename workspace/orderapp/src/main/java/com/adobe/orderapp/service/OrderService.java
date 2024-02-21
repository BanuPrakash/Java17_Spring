package com.adobe.orderapp.service;

import com.adobe.orderapp.dao.ProductDao;
import com.adobe.orderapp.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final ProductDao productDao; // wire instance of ProductDao class created by Data JPA

    public Product addProduct(Product product) {
       return productDao.save(product); //INSERT SQL
    }

    public Product getProductById(int id) {
        Optional<Product> optional =  productDao.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            return null; // need to throw Exception
        }
    }

    public List<Product> getProducts() {
        return productDao.findAll();
    }
}
