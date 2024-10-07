package com.mucanyu.brokage.order.service;

import com.mucanyu.brokage.order.constant.OrderStatus;
import com.mucanyu.brokage.order.dto.CreateOrderDto;
import com.mucanyu.brokage.order.dto.MatchOrderDto;
import com.mucanyu.brokage.order.dto.OrderDetailDto;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderDetailDto createOrder(Long customerId, CreateOrderDto createOrderDto);

    List<OrderDetailDto> listOrders(Long customerId, OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate);

    OrderDetailDto cancelOrder(Long customerId, Long orderId);

    MatchOrderDto matchPendingOrders(List<Long> pendingOrders);
}
