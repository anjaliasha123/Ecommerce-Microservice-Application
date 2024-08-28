package com.ecommerce.OrderService.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "PRODUCT-SERVICE",
        fallback = ProductServiceFallback.class,
        path = "/product"
)
public interface ProductService {
    @PutMapping(value = "/reduceQuantity/{id}")
    ResponseEntity<String> reduceQuantity(@PathVariable("id") long id, @RequestParam("quant") long quantity);
}
