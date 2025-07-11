package com.server.lifestyle.service;

import com.server.lifestyle.domain.OrderStatus;
import com.server.lifestyle.model.*;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
    Order findOrderById(Long id) throws Exception;
    List<Order> userOrderHistory(Long userId);
    List<Order> sellerOrderHistory(Long sellerId);
    Order updateOrderStatus(Long id, OrderStatus status) throws Exception;
    Order cancelOrder(Long orderId, User user) throws Exception;
    OrderItem getOrderItemById(Long orderId) throws Exception;

}
