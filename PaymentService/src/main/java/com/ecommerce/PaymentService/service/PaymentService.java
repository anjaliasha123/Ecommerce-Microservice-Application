package com.ecommerce.PaymentService.service;

import com.ecommerce.PaymentService.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
