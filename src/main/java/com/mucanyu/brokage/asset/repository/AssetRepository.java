package com.mucanyu.brokage.asset.repository;

import com.mucanyu.brokage.asset.model.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

  Page<Asset> findAllByCustomerId(Long customerId, Pageable pageable);

  Optional<Asset> findByCustomerIdAndAssetName(Long customerId, String assetName);
}
