package com.mucanyu.brokage.order.dto;

import com.mucanyu.brokage.order.constant.OrderSide;
import com.mucanyu.brokage.order.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

  private OrderStatus orderStatus;
  private OrderSide side;
  private String assetName;
  private LocalDateTime createDate;
  private BigDecimal size;
  private BigDecimal price;
}
