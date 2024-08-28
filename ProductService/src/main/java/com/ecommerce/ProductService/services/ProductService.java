package com.ecommerce.ProductService.services;

import com.ecommerce.ProductService.models.ProductRequest;
import com.ecommerce.ProductService.models.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long id);

    void reduceQuantity(long id, long quantity);
}
