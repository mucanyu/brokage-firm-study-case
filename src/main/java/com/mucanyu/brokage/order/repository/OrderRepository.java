package com.mucanyu.brokage.order.repository;

import com.mucanyu.brokage.order.constant.OrderStatus;
import com.mucanyu.brokage.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

  List<Order> findAllByIdIn(List<Long> orderIds);

  boolean existsByIdInAndStatusIsNot(List<Long> orders, OrderStatus orderStatus);
}
