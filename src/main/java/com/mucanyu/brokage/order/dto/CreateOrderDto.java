package com.mucanyu.brokage.order.dto;

import com.mucanyu.brokage.common.constants.Currency;
import com.mucanyu.brokage.order.constant.OrderSide;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record CreateOrderDto(@NotBlank String assetName,
                             @NotBlank OrderSide orderSide,
                             @NotEmpty BigDecimal size,
                             @NotEmpty BigDecimal price,
                             @NotBlank Currency currency) {}
