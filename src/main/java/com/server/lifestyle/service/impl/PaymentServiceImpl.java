package com.server.lifestyle.service.impl;

import com.server.lifestyle.domain.PaymentOrderStatus;
import com.server.lifestyle.model.Order;
import com.server.lifestyle.model.PaymentOrder;
import com.server.lifestyle.model.User;
import com.server.lifestyle.repository.OrderRepository;
import com.server.lifestyle.repository.PaymentOrderRepository;
import com.server.lifestyle.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final OrderRepository orderRepository;

    String stripeApiKey = "stripeApiKey";
    String stripeApiSecret = "stripeApiSecret";

    @Override
    public PaymentOrder createPaymentOrder(User user, Set<Order> orders) {

        double amount = orders.stream().mapToDouble(Order::getTotalSellingPrice).sum();

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setUser(user);
        paymentOrder.setOrders(orders);

        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long orderId) throws Exception {
        return paymentOrderRepository.findById(orderId).orElseThrow(()-> new Exception("Payment order not found!"));
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(paymentId);
        if (paymentOrder == null) {
            throw new Exception("payment order not found with provided payment link id");
        }
        return paymentOrder;
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {

        }
        return null;
    }

    @Override
    public String createStripePaymentLink(User user, double amount, Long orderId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/"+orderId)
                .setCancelUrl("http://localhost:3000/payment-cancel/"+orderId)
                .addLineItem(SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmount((long) (amount*100))
                                        .setProductData(
                                                SessionCreateParams
                                                        .LineItem.PriceData.ProductData.builder()
                                                        .setName("lifestyle payment")
                                                        .build()
                                        ).build()
                                ).build()
                ).build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}
