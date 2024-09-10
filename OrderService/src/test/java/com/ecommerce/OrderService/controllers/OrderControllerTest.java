package com.ecommerce.OrderService.controllers;

import com.ecommerce.OrderService.OrderServiceConfig;
import com.ecommerce.OrderService.entity.Order;
import com.ecommerce.OrderService.entity.TransactionDetails;
import com.ecommerce.OrderService.model.OrderRequest;
import com.ecommerce.OrderService.model.OrderResponse;
import com.ecommerce.OrderService.model.PaymentMode;
import com.ecommerce.OrderService.repository.OrderRepository;
import com.ecommerce.OrderService.services.OrderService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;

import static org.springframework.util.StreamUtils.copyToString;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest({"server.port=0"})
@EnableConfigurationProperties
@AutoConfigureMockMvc
@ContextConfiguration(classes = {OrderServiceConfig.class})
public class OrderControllerTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;

    private ObjectMapper objectMapper
            = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private OrderRequest getMockOrderRequest(){
        return OrderRequest.builder()
                .productId(1)
                .paymentMode(PaymentMode.CASH)
                .quantity(10)
                .totalAmount(200)
                .build();
    }
    private Order getMockOrder(){
        return Order.builder()
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .id(1)
                .amount(100)
                .quantity(200)
                .productId(2)
                .build();
    }

//    @Test
//    @DisplayName("Get Order Details - Success scenario")
//    public void test_when_getOrder_Success() throws Exception {
//
//        Order order = getMockOrder();
//        OrderResponse orderResponse = getOrderResponse(order);
//        Mockito.when(orderService.getOrderDetails(Mockito.anyLong()))
//                .thenReturn(orderResponse);
//        mockMvc.perform(MockMvcRequestBuilders.get("/order/1").contentType(MediaType.APPLICATION_JSON_VALUE));
//
//
////        MvcResult mvcResult = mockMvc.perform(
////                MockMvcRequestBuilders.get("/order/1")
////                        .contentType(MediaType.APPLICATION_JSON_VALUE)
////        ).andExpect(MockMvcResultMatchers.status().isOk())
////                .andReturn();
////        String actualResponse = mvcResult.getResponse().getContentAsString();
////        Order order = orderRepository.findById(1L).get();
////        String expectedResponse = getOrderResponse(order);
////        assertEquals(expectedResponse, actualResponse);
////        MvcResult mvcResult
////                = mockMvc.perform(MockMvcRequestBuilders.get("/order/1")
////                        .contentType(MediaType.APPLICATION_JSON_VALUE))
////                .andReturn();
////
////        String actualResponse = mvcResult.getResponse().getContentAsString();
////        Order order = orderRepository.findById(1l).get();
////        String expectedResponse = getOrderResponse(order);
////
////        assertEquals(expectedResponse,actualResponse);
//    }

    private OrderResponse getOrderResponse(Order order) throws IOException {
        OrderResponse.PaymentDetails paymentDetails = objectMapper.readValue(
                copyToString(OrderControllerTest.class.getClassLoader()
                        .getResourceAsStream("mock/GetPayment.json"), Charset.defaultCharset()),
                OrderResponse.PaymentDetails.class
        );
        paymentDetails.setPaymentStatus("SUCCESS");
        OrderResponse.ProductDetails productDetails =
                objectMapper.readValue(
                        copyToString(
                                OrderControllerTest.class.getClassLoader().getResourceAsStream("mock/GetProduct.json"),
                                Charset.defaultCharset()),
                        OrderResponse.ProductDetails.class
                );
        OrderResponse orderResponse = OrderResponse.builder()
                .paymentDetails(paymentDetails)
                .productDetails(productDetails)
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .orderId(order.getId())
                .build();
        return orderResponse;
    }

    private String getOrderResponseString(Order order) throws IOException {
        OrderResponse.PaymentDetails paymentDetails = objectMapper.readValue(
                        copyToString(OrderControllerTest.class.getClassLoader()
                                .getResourceAsStream("mock/GetPayment.json"), Charset.defaultCharset()),
                                OrderResponse.PaymentDetails.class
                );
        paymentDetails.setPaymentStatus("SUCCESS");
        OrderResponse.ProductDetails productDetails =
                objectMapper.readValue(
                        copyToString(
                                OrderControllerTest.class.getClassLoader().getResourceAsStream("mock/GetProduct.json"),
                                Charset.defaultCharset()),
                        OrderResponse.ProductDetails.class
                        );
        OrderResponse orderResponse = OrderResponse.builder()
                .paymentDetails(paymentDetails)
                .productDetails(productDetails)
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .orderId(order.getId())
                .build();
        return objectMapper.writeValueAsString(orderResponse);

    }
}