package com.mucanyu.brokage.order.controller;

import com.mucanyu.brokage.order.constant.OrderStatus;
import com.mucanyu.brokage.order.dto.CreateOrderDto;
import com.mucanyu.brokage.order.dto.MatchOrderDto;
import com.mucanyu.brokage.order.dto.OrderDetailDto;
import com.mucanyu.brokage.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{customerId}/orders")
    public ResponseEntity<OrderDetailDto> createOrder(@PathVariable Long customerId,
                                                      @RequestBody CreateOrderDto createOrderDto) {
        return ResponseEntity.ok(orderService.createOrder(customerId, createOrderDto));
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderDetailDto>> listOrders(@PathVariable Long customerId,
                                                           @RequestParam(required = false) OrderStatus orderStatus,
                                                           @RequestParam(required = false) LocalDateTime startDate,
                                                           @RequestParam(required = false) LocalDateTime endDate) {
        return ResponseEntity.ok(orderService.listOrders(customerId, orderStatus, startDate, endDate));
    }

    @DeleteMapping("/{customerId}/orders/{orderId}")
    public ResponseEntity<OrderDetailDto> cancelOrder(@PathVariable Long customerId, @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(customerId, orderId));
    }

    @PostMapping("/orders/match")
    public ResponseEntity<?> matchPendingOrders(@RequestBody MatchOrderDto matchOrderDto) {
        return ResponseEntity.ok(null);
    }
}
