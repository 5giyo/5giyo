package com.example.ogiyo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundCouponException extends ResponseStatusException {
  public NotFoundCouponException(){ super(HttpStatus.UNAUTHORIZED, ErrorCode.NOT_FOUND_COUPON.getMessage()); }
}
