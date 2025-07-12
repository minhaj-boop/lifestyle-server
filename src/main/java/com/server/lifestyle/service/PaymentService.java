package com.server.lifestyle.service;

import com.server.lifestyle.model.Order;
import com.server.lifestyle.model.PaymentOrder;
import com.server.lifestyle.model.User;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PaymentService {
    PaymentOrder createPaymentOrder(User user, Set<Order> orders);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentLinkId);
    String createStripePaymentLink(User user, double amount, Long orderId) throws StripeException;
}
