package com.ecommerce.PaymentService.service;

import com.ecommerce.PaymentService.model.PaymentRequest;
import com.ecommerce.PaymentService.responses.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetails(long paymentId);
}
