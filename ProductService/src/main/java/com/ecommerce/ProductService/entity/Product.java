package com.ecommerce.ProductService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private long price;
    @Column(name = "quantity")
    private long quantity;
}
