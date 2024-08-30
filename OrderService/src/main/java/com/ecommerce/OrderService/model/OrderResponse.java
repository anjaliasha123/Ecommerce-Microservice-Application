package com.ecommerce.OrderService.model;

import com.ecommerce.OrderService.entity.Product;
import com.ecommerce.OrderService.entity.TransactionDetails;
import com.ecommerce.OrderService.external.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private long orderId;
    private Instant orderDate;
    private String orderStatus;
    private long amount;
    private Product product;
    private TransactionDetails transactionDetails;
}
