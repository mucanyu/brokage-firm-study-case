package com.mucanyu.brokage.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetDto {

  private Long customerId;
  private String assetName;
  private BigDecimal size;
  private BigDecimal usableSize;
}
