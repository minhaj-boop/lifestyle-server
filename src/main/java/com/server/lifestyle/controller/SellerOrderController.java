package com.server.lifestyle.controller;

import com.server.lifestyle.service.OrderService;
import com.server.lifestyle.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller/order")
public class SellerOrderController {
    private final OrderService orderService;
    private final SellerService sellerService;

}
