package com.mucanyu.brokage.asset.service;

import com.mucanyu.brokage.asset.dto.AssetListDto;
import com.mucanyu.brokage.asset.model.Asset;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

public interface AssetService {

  AssetListDto findAllByCustomerId(Long customerId, Pageable pageable);

  Optional<Asset> findByCustomerIdAndAssetName(Long customerId, String assetName);

  BigDecimal addAssetSize(Long customerId, String assetName, BigDecimal amount);

  BigDecimal subtractAssetSize(Long customerId, String assetName, BigDecimal amount);

  BigDecimal addUsableSize(Long customerId, String assetName, BigDecimal amount);

  BigDecimal subtractUsableSize(Long customerId, String assetName, BigDecimal amount);

  Asset save(Asset asset);
}
