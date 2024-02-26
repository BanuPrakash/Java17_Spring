package com.adobe.orderapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // AUTO_INCREMENT

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 10, message = "Price ${validatedValue} should be more than {value}")
    private double price;

    @Column(name="qty")
    @Min(value = 1, message = "Quantity ${validatedValue} should be more than {value}")
    private int quantity;

    @Version
    private int ver;
}

