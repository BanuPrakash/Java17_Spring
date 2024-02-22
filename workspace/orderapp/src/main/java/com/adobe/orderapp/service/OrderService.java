package com.adobe.orderapp.service;

import com.adobe.orderapp.dao.CustomerDao;
import com.adobe.orderapp.dao.ProductDao;
import com.adobe.orderapp.entity.Customer;
import com.adobe.orderapp.entity.Product;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final ProductDao productDao; // wire instance of ProductDao class created by Data JPA
    private final CustomerDao customerDao;

    public void saveCustomer(Customer c) {
        customerDao.save(c);
    }

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

    public List<Product> getProductsByRange(double low, double high) {
        return productDao.getProductsByRange(low, high);
    }

    @Transactional
    public Product modifyProduct( int id, double price) {
        productDao.updateProduct(id, price);
        return getProductById(id);
    }
}
