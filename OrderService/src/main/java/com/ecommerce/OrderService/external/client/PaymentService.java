package com.ecommerce.OrderService.external.client;

import com.ecommerce.OrderService.exceptions.CustomException;
import com.ecommerce.OrderService.external.response.PaymentResponse;
import com.ecommerce.OrderService.model.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "PAYMENT-SERVICE",
        path = "/payment"
)
@CircuitBreaker(name = "external", fallbackMethod = "fallback")
public interface PaymentService {
    @PostMapping
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);
    @GetMapping("/{orderId}")
    ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable("orderId") long orderId);

    default void fallback(int id, Exception e){
        throw new CustomException(
                "Payment service is not available",
                "UNAVAILABLE",
                500
        );
    }
}
