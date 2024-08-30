package com.ecommerce.PaymentService.controller;

import com.ecommerce.PaymentService.model.PaymentRequest;
import com.ecommerce.PaymentService.responses.PaymentResponse;
import com.ecommerce.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody  PaymentRequest paymentRequest){
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest),
                HttpStatus.OK
        );
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable("orderId") long orderId){
        PaymentResponse paymentResponse = paymentService.getPaymentDetails(orderId);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
