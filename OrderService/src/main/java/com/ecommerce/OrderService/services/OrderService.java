package com.ecommerce.OrderService.services;

import com.ecommerce.OrderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
