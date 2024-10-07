package com.mucanyu.brokage.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BalanceDto {

  private BigDecimal size;
  private BigDecimal usableSize;
}
