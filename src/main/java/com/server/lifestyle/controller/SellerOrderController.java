package com.server.lifestyle.controller;

import com.server.lifestyle.domain.OrderStatus;
import com.server.lifestyle.model.Order;
import com.server.lifestyle.model.Seller;
import com.server.lifestyle.service.OrderService;
import com.server.lifestyle.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller/order")
public class SellerOrderController {
    private final OrderService orderService;
    private final SellerService sellerService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("Authorization") String jwtToken) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwtToken);
        List<Order> orders = orderService.sellerOrder(seller.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/update/{orderStatus}")
    public ResponseEntity<Order> updateOrder(@RequestHeader("Authorization") String jwtToken, @PathVariable Long orderId, @PathVariable OrderStatus orderStatus) throws Exception {

        Order order = orderService.updateOrderStatus(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);

    }

}
