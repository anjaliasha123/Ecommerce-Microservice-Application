package com.ecommerce.OrderService.services;

import com.ecommerce.OrderService.entity.Order;
import com.ecommerce.OrderService.entity.Product;
import com.ecommerce.OrderService.entity.TransactionDetails;
import com.ecommerce.OrderService.exceptions.CustomException;
import com.ecommerce.OrderService.external.client.PaymentService;
import com.ecommerce.OrderService.external.client.ProductService;
import com.ecommerce.OrderService.external.response.PaymentResponse;
import com.ecommerce.OrderService.external.response.ProductResponse;
import com.ecommerce.OrderService.model.OrderRequest;
import com.ecommerce.OrderService.model.OrderResponse;
import com.ecommerce.OrderService.model.PaymentRequest;
import com.ecommerce.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing order request {}", orderRequest);
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        log.info("Creating order with status created");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);
        log.info("calling payment service for payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();
        String orderStatus = null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("payment done successfully");
            orderStatus = "PLACED";
        }catch(Exception e){
            log.error("Error occured in payment");
            orderStatus = "PAYMENT_FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("order placed with order id {}", order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("get details for order id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        ()-> new CustomException("Order not found for order id: "+orderId, "NOT_FOUND", 404)
                );
        ProductResponse productResponse = productService.getProductById(order.getProductId()).getBody();
        Product product = Product.builder()
                .productId(productResponse.getProductId())
                .price(productResponse.getPrice())
                .productName(productResponse.getProductName())
                .build();
        PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrderId(orderId).getBody();
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .amount(paymentResponse.getAmount())
                .paymentDate(paymentResponse.getPaymentDate())
                .referenceNumber(paymentResponse.getReferenceNumber())
                .paymentMode(paymentResponse.getPaymentMode())
                .paymentStatus(paymentResponse.getPaymentStatus())
                .build();
        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(orderId)
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .product(product)
                .transactionDetails(transactionDetails)
                .build();

        return orderResponse;
    }
}
