package com.ecommerce.OrderService;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;

//to make the testInstanceServiceRegistryListSupplier available throught test or orderController
@TestConfiguration
public class OrderServiceConfig {
    @Bean
    public ServiceInstanceListSupplier supplier(){
        return new TestServiceRegistryInstanceListSupplier();
    }
}
