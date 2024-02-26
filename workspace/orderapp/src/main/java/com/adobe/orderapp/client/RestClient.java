package com.adobe.orderapp.client;

import com.adobe.orderapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@Component
@RequiredArgsConstructor
public class RestClient implements CommandLineRunner {
    private final RestTemplate restTemplate;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Get Users :");
        getUsers();
        System.out.println("Get Product :");
        getProduct();
        System.out.println("Add Product :");
        addNewProduct();
        System.out.println("Get All Products: ");
        getAllProducts();
    }

    private void getAllProducts() {
        ResponseEntity<List<Product>> response =
                restTemplate.exchange("http://localhost:8080/api/products",
                        HttpMethod.GET,
                        null, // payload
                        new ParameterizedTypeReference<List<Product>>() {
                        });

        for(Product product : response.getBody()) {
            System.out.println(product);
        }
    }

    private void addNewProduct() {
        Product p = Product.builder().name("OnePlus").price(75000.00).quantity(100).build();
        ResponseEntity<Product> response =
                restTemplate.postForEntity("http://localhost:8080/api/products",p, Product.class);
        System.out.println(response.getStatusCode()); //201 CREATED
        System.out.println(response.getBody());
    }

    private  void getProduct() {
       ResponseEntity<Product> response =
               restTemplate.getForEntity("http://localhost:8080/api/products/2", Product.class);
        System.out.println(response.getStatusCode()); //200 OK
        System.out.println(response.getBody()); // Product
    }
    private void getUsers() {
        String result = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users", String.class);
        // ObjectMapper JSON --> Java
        System.out.println(result);
    }
}
