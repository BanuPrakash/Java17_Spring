package com.adobe.orderapp.client;

import com.adobe.orderapp.entity.Customer;
import com.adobe.orderapp.entity.Item;
import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
//@org.springframework.core.annotation.Order(1)
public class OrderClient implements CommandLineRunner {
    private final OrderService service;
    @Override
    public void run(String... args) throws Exception {
        newOrder();
    }

    private void newOrder() {
        Order order = new Order();
        order.setCustomer(Customer.builder().email("swetha@adobe.com").build());

        Item i1 = Item.builder().product(Product.builder().id(2).build()).quantity(2).build();
        Item i2 = Item.builder().product(Product.builder().id(1).build()).quantity(3).build();
        order.getItems().add(i1);
        order.getItems().add(i2);

        service.placeOrder(order);
    }
}
