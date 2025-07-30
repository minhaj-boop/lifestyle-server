package com.server.lifestyle.controller;

import com.server.lifestyle.domain.PaymentMethod;
import com.server.lifestyle.model.*;
import com.server.lifestyle.repository.PaymentOrderRepository;
import com.server.lifestyle.response.PaymentLinkResponse;
import com.server.lifestyle.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final PaymentService paymentService;
    private final PaymentOrderRepository paymentOrderRepository;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createOrder(
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwtToken
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        Cart cart = cartService.findUserCart(user);

        Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);

        PaymentOrder paymentOrder = paymentService.createPaymentOrder(user, orders);
        PaymentLinkResponse res = new PaymentLinkResponse();

        String paymentUrl = paymentService.createStripePaymentLink(
                user,
                paymentOrder.getAmount(),
                paymentOrder.getId());
        res.setPayment_link_id(paymentUrl);

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/history/user")
    public ResponseEntity<List<Order>> getUserOderHistory(@RequestHeader("Authorization") String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/get/by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/item/get/by-id/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long orderItemId, @RequestHeader("Authorization") String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        OrderItem orderItem = orderService.getOrderItemById(orderItemId);

        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        Order order = orderService.cancelOrder(orderId, user);

        Seller seller = sellerService.getSellerById(order.getSellerId());
        SellerReport sellerReport = sellerReportService.getSellerReport(seller);
        sellerReport.setCancelledOrders(sellerReport.getCancelledOrders()+1);
        sellerReport.setTotalRefunds(sellerReport.getTotalRefunds()+order.getTotalSellingPrice());

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
