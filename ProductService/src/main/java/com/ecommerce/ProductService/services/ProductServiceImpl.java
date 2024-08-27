package com.ecommerce.ProductService.services;

import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.exceptions.ProductServiceCustomException;
import com.ecommerce.ProductService.models.ProductRequest;
import com.ecommerce.ProductService.models.ProductResponse;
import com.ecommerce.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("adding product");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long id) {
        log.info("getting product for id: "+id);
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductServiceCustomException("product with given id not found", "PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }
}
