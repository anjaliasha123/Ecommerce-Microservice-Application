package com.ecommerce.ProductService.controllers;

import com.ecommerce.ProductService.models.ProductRequest;
import com.ecommerce.ProductService.models.ProductResponse;
import com.ecommerce.ProductService.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<String> reduceQuantity(@PathVariable("id") long id,@RequestParam("quant") long quantity){
        productService.reduceQuantity(id, quantity);
        return new ResponseEntity<>("Successfully reduced quantity",HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long id){
        ProductResponse productResponse = productService.getProductById(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
}
