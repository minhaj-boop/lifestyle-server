package com.server.lifestyle.model;

import com.server.lifestyle.domain.PaymentStatus;
import lombok.Data;

@Data
public class PaymentDetails {
    private String paymentId;
    private String paymentLinkId;
    private String paymentLinkReferenceId;
    private String paymentLinkStatus;
    private String paymentIdZWSP;
//    private PaymentStatus paymentStatus;
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
}
