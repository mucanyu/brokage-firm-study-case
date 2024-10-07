package com.mucanyu.brokage.common.exception;

import com.mucanyu.brokage.common.constants.ErrorCode;
import jakarta.validation.constraints.NotNull;

public record BaseException(@NotNull ErrorCode errorCode) {}
