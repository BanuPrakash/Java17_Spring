package com.adobe.orderapp.client;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductClient implements CommandLineRunner {
    private final OrderService service;

    @Override
    public void run(String... args) throws Exception {
      //  addProduct();
        printProducts();
    }

    public void addProduct() {
        service.addProduct(Product.builder().
                 name("iPhone 14")
                .price(89000.00)
                .quantity(100).
                 build());
        service.addProduct(Product.builder().
                name("Wacom")
                .price(4500.00)
                .quantity(100).
                build());
    }

    public void printProducts() {
        List<Product> products = service.getProducts();
        for(Product p : products) {
            System.out.println(p); // toString
        }
    }
}
