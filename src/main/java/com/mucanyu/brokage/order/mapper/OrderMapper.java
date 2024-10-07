package com.mucanyu.brokage.order.mapper;

import com.mucanyu.brokage.order.dto.OrderDetailDto;
import com.mucanyu.brokage.order.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

  public OrderDetailDto toOrderDetailDto(Order order) {
    return OrderDetailDto.builder()
        .orderStatus(order.getStatus())
        .side(order.getSide())
        .price(order.getPrice())
        .size(order.getSize())
        .assetName(order.getAssetName())
        .createDate(order.getCreateDate())
        .build();
  }
}
