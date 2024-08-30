package com.ecommerce.OrderService.external.client;

import com.ecommerce.OrderService.external.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceFallback implements ProductService{
    @Override
    public ResponseEntity<String> reduceQuantity(long id, long quantity) {
        return null;
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(long id) {
        return null;
    }
}
