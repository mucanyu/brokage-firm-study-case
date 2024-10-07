package com.mucanyu.brokage.asset.controller;

import com.mucanyu.brokage.asset.dto.AssetListDto;
import com.mucanyu.brokage.asset.service.AssetService;
import com.mucanyu.brokage.common.constants.Currency;
import com.mucanyu.brokage.asset.dto.BalanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AssetController {

  private final AssetService assetService;

  @GetMapping("/{customerId}/assets")
  public ResponseEntity<AssetListDto> listAssets(@PathVariable Long customerId,
                                                 @RequestParam(defaultValue = "0") Integer pageNumber,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
    return ResponseEntity.ok(assetService.findAllByCustomerId(customerId, PageRequest.of(pageNumber, pageSize)));
  }

  @PostMapping("/{customerId}/deposit")
  public ResponseEntity<BalanceDto> depositMoney(@PathVariable Long customerId, @RequestParam BigDecimal amount) {
    BigDecimal sizeDto = assetService.addAssetSize(customerId, Currency.TRY.name(), amount);
    BigDecimal usableSizeDto = assetService.addUsableSize(customerId, Currency.TRY.name(), amount);

    return ResponseEntity.ok(new BalanceDto(sizeDto, usableSizeDto));
  }

  @PostMapping("/{customerId}/withdraw")
  public ResponseEntity<BalanceDto> withdrawMoney(@PathVariable Long customerId, @RequestParam BigDecimal amount) {
    BigDecimal sizeDto = assetService.subtractAssetSize(customerId, Currency.TRY.name(), amount);
    BigDecimal usableSizeDto = assetService.subtractUsableSize(customerId, Currency.TRY.name(), amount);

    return ResponseEntity.ok(new BalanceDto(sizeDto, usableSizeDto));
  }
}
