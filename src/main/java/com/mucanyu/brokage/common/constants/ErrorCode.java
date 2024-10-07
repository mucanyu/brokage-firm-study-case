package com.mucanyu.brokage.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  UNKNOWN_ERROR("ERR-0000", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String errorCode;
  private final HttpStatus httpStatus;
}
