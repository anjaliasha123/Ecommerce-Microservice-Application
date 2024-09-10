package com.ecommerce.OrderService.services;

import com.ecommerce.OrderService.entity.Order;
import com.ecommerce.OrderService.exceptions.CustomException;
import com.ecommerce.OrderService.external.client.PaymentService;
import com.ecommerce.OrderService.external.client.ProductService;
import com.ecommerce.OrderService.external.response.PaymentResponse;
import com.ecommerce.OrderService.external.response.ProductResponse;
import com.ecommerce.OrderService.model.OrderRequest;
import com.ecommerce.OrderService.model.OrderResponse;
import com.ecommerce.OrderService.model.PaymentMode;
import com.ecommerce.OrderService.model.PaymentRequest;
import com.ecommerce.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Log4j2
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @DisplayName("Get Order Details - Success")
    @Test
    void test_When_Order_Success(){
        //mocking
        Order order = getMockOrder();

        Mockito.when(orderRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(order));
        Mockito.when(productService.getProductById(order.getProductId()))
                        .thenReturn(getMockProductResponse());
        Mockito.when(paymentService.getPaymentDetailsByOrderId(order.getId()))
                .thenReturn(getMockPaymentResponse());

        //actual
        OrderResponse orderResponse = orderService.getOrderDetails(1);

        //verification
        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(productService, Mockito.times(1)).getProductById(order.getProductId());
        Mockito.verify(paymentService, Mockito.times(1)).getPaymentDetailsByOrderId(order.getId());

        //assert
        assertNotNull(orderResponse);
        assertEquals(order.getId(), orderResponse.getOrderId());

    }

    @DisplayName("Get Order Details - Failure")
    @Test
    void test_When_Get_Order_NOT_FOUND_then_Not_Found(){
        Mockito.when(orderRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(null));
        CustomException exception = assertThrows(CustomException.class, ()-> orderService.getOrderDetails(1));
        assertEquals("NOT_FOUND", exception.getErrorCode());
        assertEquals(404, exception.getStatus());

        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @DisplayName("Place Order - Success")
    @Test
    void test_when_place_order_success(){
//        mocking
        Order order = getMockOrder();
        OrderRequest orderRequest = getMockOrderRequest();
        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenReturn(order);
        Mockito.when(paymentService.doPayment(Mockito.any(PaymentRequest.class)))
                .thenReturn(new ResponseEntity<>(1L, HttpStatus.OK));
        Mockito.when(productService.reduceQuantity(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>("Successfully reduced quantity", HttpStatus.OK));

//        ACTUAL
        long orderId = orderService.placeOrder(orderRequest);

//        verification
        Mockito.verify(orderRepository, Mockito.times(2)).save(Mockito.any());
        Mockito.verify(paymentService, Mockito.times(1)).doPayment(Mockito.any(PaymentRequest.class));
        Mockito.verify(productService, Mockito.times(1))
                .reduceQuantity(Mockito.anyLong(),Mockito.anyLong());

//        assertion
        assertEquals(order.getId(), orderId);
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
    private OrderRequest getMockOrderRequest() {
        return OrderRequest.builder()
                .productId(1)
                .quantity(10)
                .paymentMode(PaymentMode.CASH)
                .totalAmount(100)
                .build();
    }

    private ResponseEntity<PaymentResponse> getMockPaymentResponse() {
        return new ResponseEntity<>(
                PaymentResponse.builder()
                        .paymentDate(Instant.now())
                        .paymentId(1)
                        .paymentMode(PaymentMode.CASH)
                        .amount(200)
                        .orderId(1)
                        .status("ACCEPTED")
                        .build(),
                HttpStatus.OK
        );
    }

    private ResponseEntity<ProductResponse> getMockProductResponse() {
        return new ResponseEntity<>(ProductResponse.builder()
                .productId(2)
                .productName("iPhone")
                .price(100)
                .quantity(200)
                .build(), HttpStatus.OK);
    }
}