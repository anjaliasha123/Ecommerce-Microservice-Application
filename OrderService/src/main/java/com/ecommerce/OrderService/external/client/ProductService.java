package com.ecommerce.OrderService.external.client;

import com.ecommerce.OrderService.exceptions.CustomException;
import com.ecommerce.OrderService.external.response.ProductResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "PRODUCT-SERVICE",
        fallback = ProductServiceFallback.class,
        path = "/product"
)
@CircuitBreaker(name = "product-external", fallbackMethod = "fallback")
public interface ProductService {
    @PutMapping(value = "/reduceQuantity/{id}")
    ResponseEntity<String> reduceQuantity(@PathVariable("id") long id, @RequestParam("quant") long quantity);
    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long id);

    default void fallback(int id, Exception e){
        throw new CustomException(
                "Product Service is unavailable",
                "UNAVAILABLE",
                500
        );
    }
}
