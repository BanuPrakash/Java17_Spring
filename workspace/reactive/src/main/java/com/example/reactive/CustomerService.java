package com.example.reactive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;

    public List<Customer> loadCustomers() {
        long startTime = System.currentTimeMillis();
            List<Customer> customers = customerDao.getCustomers();
        long endTime = System.currentTimeMillis();
        System.out.println("Total Execution time " + (endTime - startTime) + " ms");
        return  customers;
    }

    public Flux<Customer> loadCustomerStream() {
        long startTime = System.currentTimeMillis();
        Flux<Customer> customerPublisher = customerDao.getCustomersList();
        long endTime = System.currentTimeMillis();
        System.out.println("Total Execution time Stream" + (endTime - startTime) + " ms");
        return  customerPublisher;
    }
}
