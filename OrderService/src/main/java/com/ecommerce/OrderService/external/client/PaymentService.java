package com.ecommerce.OrderService.external.client;

import com.ecommerce.OrderService.model.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "PAYMENT-SERVICE",
        fallback = ProductServiceFallback.class,
        path = "/payment"
)
public interface PaymentService {
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);
}
