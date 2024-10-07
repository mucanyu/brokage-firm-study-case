package com.mucanyu.brokage.order.service;

import com.mucanyu.brokage.asset.model.Asset;
import com.mucanyu.brokage.asset.service.AssetService;
import com.mucanyu.brokage.common.constants.Currency;
import com.mucanyu.brokage.auth.service.CustomerService;
import com.mucanyu.brokage.order.constant.OrderSide;
import com.mucanyu.brokage.order.constant.OrderStatus;
import com.mucanyu.brokage.order.dto.CreateOrderDto;
import com.mucanyu.brokage.order.dto.MatchOrderDto;
import com.mucanyu.brokage.order.dto.OrderDetailDto;
import com.mucanyu.brokage.order.mapper.OrderMapper;
import com.mucanyu.brokage.order.model.Order;
import com.mucanyu.brokage.order.repository.OrderRepository;
import com.mucanyu.brokage.order.repository.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final CustomerService customerService;
  private final AssetService assetService;
  private final OrderMapper orderMapper;

  @Override
  @Transactional
  @PreAuthorize("#customerId == authentication.principal.customer.customerId or hasRole('ROLE_ADMIN')")
  public OrderDetailDto createOrder(Long customerId, CreateOrderDto createOrderDto) {

    if (!customerService.existsById(customerId)) {
      throw new RuntimeException("TODO : Customer not found");
    }

    var asset = assetService
        .findByCustomerIdAndAssetName(customerId, Currency.TRY.name())
        .orElseThrow(() ->  new RuntimeException("TODO : Asset not found"));

    var orderTotalPrice = createOrderDto.size().multiply(createOrderDto.price());
    if (OrderSide.BUY.equals(createOrderDto.orderSide())
        && asset.getUsableSize().compareTo(orderTotalPrice) < 0) {
      throw new RuntimeException("TODO : Insufficient TRY balance");
    }

    var order = Order.builder()
        .customerId(customerId)
        .assetName(createOrderDto.assetName())
        .status(OrderStatus.PENDING)
        .createDate(LocalDateTime.now())
        .side(createOrderDto.orderSide())
        .price(createOrderDto.price())
        .size(createOrderDto.size())
        .build();

    if (OrderSide.BUY.equals(order.getSide())) {
      // Reserve money
      var amount = order.getPrice().multiply(order.getSize());
      assetService.subtractUsableSize(customerId, Currency.TRY.name(), amount);
    }

    return orderMapper.toOrderDetailDto(orderRepository.save(order));
  }

  @Override
  @Transactional
  @PreAuthorize("#customerId == authentication.principal.customer.customerId or hasRole('ROLE_ADMIN')")
  public List<OrderDetailDto> listOrders(Long customerId, OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate) {
    return orderRepository.findAll(
        OrderSpecification.filterOrders(customerId, orderStatus, startDate, endDate))
        .stream()
        .map(orderMapper::toOrderDetailDto)
        .toList();
  }

  @Override
  @Transactional
  @PreAuthorize("#customerId == authentication.principal.customer.customerId or hasRole('ROLE_ADMIN')")
  public OrderDetailDto cancelOrder(Long customerId, Long orderId) {
    var order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    if (!OrderStatus.PENDING.equals(order.getStatus())) {
      throw new IllegalArgumentException("TODO : Orders with a status of 'PENDING' can only be cancelled");
    }

    if (OrderSide.BUY.equals(order.getSide())) {
      // Refund reserved money
      var amount =  order.getPrice().multiply(order.getSize());
      assetService.addUsableSize(customerId, Currency.TRY.name(), amount);
    }

    order.setStatus(OrderStatus.CANCELLED);

    return orderMapper.toOrderDetailDto(orderRepository.save(order));
  }

  @Override
  @Transactional
  public MatchOrderDto matchPendingOrders(List<Long> pendingOrders) {

    if (orderRepository.existsByIdInAndStatusIsNot(pendingOrders, OrderStatus.PENDING)) {
      throw new RuntimeException("TODO : Status of the orders must be 'PENDING'");
    }

    orderRepository
        .findAllByIdIn(pendingOrders)
        .forEach(
            order -> {
              if (OrderSide.BUY.equals(order.getSide())) {
                assetService.subtractAssetSize(order.getCustomerId(), Currency.TRY.name(), order.getSize().multiply(order.getPrice()));

                assetService
                    .findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
                    .ifPresentOrElse(
                        asset -> {
                          asset.setSize(asset.getSize().add(order.getSize()));
                          asset.setUsableSize(asset.getUsableSize().add(order.getSize()));
                        },
                        () ->
                            assetService.save(
                                Asset.builder()
                                    .customerId(order.getCustomerId())
                                    .assetName(order.getAssetName())
                                    .size(order.getSize())
                                    .usableSize(order.getSize())
                                    .build()));

              } else {
                assetService.addAssetSize(
                    order.getCustomerId(), Currency.TRY.name(), order.getSize().multiply(order.getPrice()));
                assetService.addUsableSize(
                    order.getCustomerId(), Currency.TRY.name(), order.getSize().multiply(order.getPrice()));

                assetService.subtractAssetSize(order.getCustomerId(), order.getAssetName(), order.getSize());
                assetService.subtractUsableSize(order.getCustomerId(), order.getAssetName(), order.getSize());
              }
            });

    return new MatchOrderDto(pendingOrders);
  }

}
