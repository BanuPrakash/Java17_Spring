package com.example.reactive;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping
    public List<Customer> getCustomers() {
        return  service.loadCustomers();
    }

    // SSE Server Side Event
    @GetMapping(value="/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getStream() {
        return  service.loadCustomerStream();
    }
}