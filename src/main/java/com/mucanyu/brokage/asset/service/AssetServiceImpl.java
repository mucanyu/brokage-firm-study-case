package com.mucanyu.brokage.asset.service;

import com.mucanyu.brokage.asset.dto.AssetListDto;
import com.mucanyu.brokage.asset.mapper.AssetMapper;
import com.mucanyu.brokage.asset.model.Asset;
import com.mucanyu.brokage.asset.repository.AssetRepository;
import com.mucanyu.brokage.common.dto.PageMetadataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

  private final AssetRepository assetRepository;
  private final AssetMapper assetMapper;

  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("#customerId == authentication.principal.customer.customerId or hasRole('ROLE_ADMIN')")
  public AssetListDto findAllByCustomerId(Long customerId, Pageable pageable) {
    var assets = assetRepository.findAllByCustomerId(customerId, pageable);

    var assetDtoList = assets.map(assetMapper::toAssetDto).toList();

    return new AssetListDto(assetDtoList, PageMetadataResponse.builder()
        .page(assets.getPageable().getPageNumber())
        .pageSize(assets.getPageable().getPageSize())
        .totalElements(assets.getTotalElements())
        .build());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Asset> findByCustomerIdAndAssetName(Long customerId, String assetName) {
    return assetRepository.findByCustomerIdAndAssetName(customerId, assetName);
  }

  @Retryable(retryFor = OptimisticLockingFailureException.class,
      maxAttempts = 3,
      backoff = @Backoff(delay = 1000))  // Delay of 1 second between retries
  @Override
  @Transactional
  @PreAuthorize("#customerId == authentication.principal.customer.customerId or hasRole('ROLE_ADMIN')")
  public BigDecimal addAssetSize(Long customerId, String assetName, BigDecimal amount) {
    var tryAsset =
        assetRepository
            .findByCustomerIdAndAssetName(customerId, assetName)
            .orElseThrow(() -> new RuntimeException("TODO"));

    tryAsset.setSize(tryAsset.getSize().add(amount));

    assetRepository.save(tryAsset);

    return tryAsset.getSize();
  }

  @Retryable(retryFor = OptimisticLockingFailureException.class,
      maxAttempts = 3,
      backoff = @Backoff(delay = 1000))
  @Override
  @Transactional
  @PreAuthorize("#customerId == authentication.principal.customer.customerId or hasRole('ROLE_ADMIN')")
  public BigDecimal subtractAssetSize(Long customerId, String assetName, BigDecimal amount) {

    var tryAsset =
        assetRepository
            .findByCustomerIdAndAssetName(customerId, assetName)
            .orElseThrow(() -> new RuntimeException("TODO"));

    if (tryAsset.getUsableSize().compareTo(amount) < 0) {
      throw new IllegalArgumentException("TODO : Insufficient balance");
    }

    tryAsset.setSize(tryAsset.getSize().subtract(amount));
    tryAsset.setUsableSize(tryAsset.getUsableSize().subtract(amount));

    assetRepository.save(tryAsset);

    return tryAsset.getSize();
  }

  @Retryable(retryFor = OptimisticLockingFailureException.class,
      maxAttempts = 3,
      backoff = @Backoff(delay = 1000))
  @Override
  @Transactional
  @PreAuthorize("#customerId == authentication.principal.customer.customerId or hasRole('ROLE_ADMIN')")
  public BigDecimal addUsableSize(Long customerId, String assetName, BigDecimal amount) {

    var tryAsset =
        assetRepository
            .findByCustomerIdAndAssetName(customerId, assetName)
            .orElseThrow(() -> new RuntimeException("TODO"));

    if (tryAsset.getUsableSize().compareTo(amount) < 0) {
      throw new IllegalArgumentException("TODO : Insufficient balance");
    }

    tryAsset.setUsableSize(tryAsset.getUsableSize().add(amount));

    assetRepository.save(tryAsset);

    return tryAsset.getUsableSize();
  }

  @Retryable(retryFor = OptimisticLockingFailureException.class,
      maxAttempts = 3,
      backoff = @Backoff(delay = 1000))
  @Override
  @Transactional
  @PreAuthorize("#customerId == authentication.principal.customer.customerId or hasRole('ROLE_ADMIN')")
  public BigDecimal subtractUsableSize(Long customerId, String assetName, BigDecimal amount) {

    var tryAsset =
        assetRepository
            .findByCustomerIdAndAssetName(customerId, assetName)
            .orElseThrow(() -> new RuntimeException("TODO"));

    if (tryAsset.getUsableSize().compareTo(amount) < 0) {
      throw new IllegalArgumentException("TODO : Insufficient balance");
    }

    tryAsset.setUsableSize(tryAsset.getUsableSize().subtract(amount));

    assetRepository.save(tryAsset);

    return tryAsset.getUsableSize();
  }

  @Override
  public Asset save(Asset asset) {
    return assetRepository.save(asset);
  }
}
