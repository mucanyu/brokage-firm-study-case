package com.mucanyu.brokage.asset.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ASSETS")
public class Asset {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "CUSTOMER_ID", nullable = false)
  private Long customerId;

  @Column(name = "ASSET_NAME", nullable = false)
  private String assetName;

  @Column(name = "SIZE", nullable = false)
  private BigDecimal size;

  @Column(name = "USABLE_SIZE", nullable = false)
  private BigDecimal usableSize;

  @Version
  @Column(name = "VERSION")
  private Long version;
}
