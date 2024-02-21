package com.adobe.orderapp.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    private String email; // PK
    @Column(name="FNAME", length = 100)
    private String firstName;
    @Column(name ="LNAME", length = 100)
    private String lastName;
}

// CustomerDAO, ...JP-QL and SQL