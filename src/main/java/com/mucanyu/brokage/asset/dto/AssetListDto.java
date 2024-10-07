package com.mucanyu.brokage.asset.dto;

import com.mucanyu.brokage.common.dto.PageMetadataResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetListDto {

  private List<AssetDto> assets;
  private PageMetadataResponse pageMetadata;
}
