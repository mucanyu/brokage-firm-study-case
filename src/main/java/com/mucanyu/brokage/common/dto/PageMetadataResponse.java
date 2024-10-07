package com.mucanyu.brokage.common.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageMetadataResponse {
  private Integer page;
  private Integer pageSize;
  private Long totalElements;
}
