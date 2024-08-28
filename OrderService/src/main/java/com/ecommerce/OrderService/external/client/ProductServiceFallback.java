package com.ecommerce.OrderService.external.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceFallback implements ProductService{
    @Override
    public ResponseEntity<String> reduceQuantity(long id, long quantity) {
        return null;
    }
}
