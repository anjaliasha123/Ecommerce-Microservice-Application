package com.ecommerce.ProductService.exceptions;

import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Data
public class ProductServiceCustomException extends RuntimeException{
    private String errorCode;

    public ProductServiceCustomException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
