package com.mucanyu.brokage.asset.mapper;

import com.mucanyu.brokage.asset.dto.AssetDto;
import com.mucanyu.brokage.asset.model.Asset;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

  public AssetDto toAssetDto(Asset asset) {
    return AssetDto.builder()
        .customerId(asset.getCustomerId())
        .assetName(asset.getAssetName())
        .size(asset.getSize())
        .usableSize(asset.getUsableSize())
        .build();
  }
}
