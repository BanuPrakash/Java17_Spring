package com.adobe.orderapp.service;

import com.adobe.orderapp.dao.CustomerDao;
import com.adobe.orderapp.dao.OrderDao;
import com.adobe.orderapp.dao.ProductDao;
import com.adobe.orderapp.entity.Customer;
import com.adobe.orderapp.entity.Item;
import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.entity.Product;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@AllArgsConstructor
public class OrderService {
    private final ProductDao productDao; // wire instance of ProductDao class created by Data JPA
    private final CustomerDao customerDao;
    private final OrderDao orderDao;

    /*
        total is computed
        orderDate --> System Date
        {
            "customer": {"email":"swetha@adobe.com"},
            "items": [
                {
                    "product": {id: 2},
                    "quantity": 1
                },
                 {
                    "product": {id: 1},
                    "quantity": 4
                }
            ]
        }
     */
    @Transactional
    public void placeOrder(Order order) {
        double total = 0;
        List<Item> items = order.getItems();
        for(Item item : items) {
            Product p = productDao.findById(item.getProduct().getId()).get();
            if(p.getQuantity() < item.getQuantity()) {
                throw  new IllegalArgumentException("Product " + p.getId() + " not in stock");
            }
            item.setAmount(p.getPrice() * item.getQuantity());// add discount + tax
            p.setQuantity(p.getQuantity() - item.getQuantity()); // Dirty checking --> Sync by Update SQL
            total += item.getAmount();
        }
        order.setTotal(total);
        orderDao.save(order);//Cascade takes care of saving items
    }

    public List<Order> getOrders() {
        return orderDao.findAll();
    }

    public void saveCustomer(Customer c) {
        customerDao.save(c);
    }

    public Product addProduct(Product product) {
       return productDao.save(product); //INSERT SQL
    }

    public Product getProductById(int id) throws  EntityNotFoundException{
        Optional<Product> optional =  productDao.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new EntityNotFoundException("Product with id " + id + " doesn't exist!!!"); // need to throw Exception
        }
    }

    public List<Product> getProducts() {
        return productDao.findAll();
    }

    public List<Product> getProductsByRange(double low, double high) {
        return productDao.getProductsByRange(low, high);
    }

    @Transactional
    public Product modifyProduct( int id, double price) throws  EntityNotFoundException{
        productDao.updateProduct(id, price);
        return getProductById(id);
    }
}
