package com.mucanyu.brokage.common.exception;

import com.mucanyu.brokage.common.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(OptimisticLockingFailureException.class)
  public ResponseEntity<String> handleOptimisticLockingFailure(
      OptimisticLockingFailureException exception) {
    log.error("Transaction conflict occurred", exception);
    return new ResponseEntity<>("Transaction conflict occurred. Please try again", HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<BaseException> handleException(Exception ex, WebRequest request) {
    log.error("Handling error: ", ex);
    var baseException = new BaseException(ErrorCode.UNKNOWN_ERROR);
    return new ResponseEntity<>(baseException, baseException.errorCode().getHttpStatus());
  }
}
