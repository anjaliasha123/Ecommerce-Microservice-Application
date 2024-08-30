package com.ecommerce.PaymentService.service;

import com.ecommerce.PaymentService.entity.TransactionDetails;
import com.ecommerce.PaymentService.model.PaymentRequest;
import com.ecommerce.PaymentService.repository.TransactionDetailsRepository;
import com.ecommerce.PaymentService.responses.PaymentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("recording payment details: {}", paymentRequest);
        TransactionDetails transactionDetails =
                TransactionDetails.builder()
                        .paymentDate(Instant.now())
                        .paymentMode(paymentRequest.getPaymentMode().name())
                        .paymentStatus("Success")
                        .orderId(paymentRequest.getOrderId())
                        .referenceNumber(UUID.randomUUID().toString())
                        .amount(paymentRequest.getAmount())
                        .build();
        transactionDetailsRepository.save(transactionDetails);
        log.info("transaction completed with id : {}", transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetails(long orderId) {
        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId);
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .orderId(transactionDetails.getOrderId())
                .paymentStatus(transactionDetails.getPaymentStatus())
                .paymentDate(transactionDetails.getPaymentDate())
                .paymentMode(transactionDetails.getPaymentMode())
                .referenceNumber(transactionDetails.getReferenceNumber())
                .amount(transactionDetails.getAmount())
                .build();
        return paymentResponse;
    }
}
