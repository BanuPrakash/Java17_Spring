package com.adobe.orderapp;

import com.adobe.orderapp.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication

public class OrderappApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderappApplication.class, args);
    }

}
