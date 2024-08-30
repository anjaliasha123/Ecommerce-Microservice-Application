package com.ecommerce.PaymentService.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long orderId;
    private String paymentMode;
    private String referenceNumber;
    private Instant paymentDate;
    private String paymentStatus;
    private long amount;
}
