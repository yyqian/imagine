package com.yyqian.imagine.exception;

/**
 * Created by yyqian on 12/7/15.
 * 400 Bad Request
 */
public class BadRequestException extends RuntimeException {
  public BadRequestException() {
  }

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadRequestException(Throwable cause) {
    super(cause);
  }

  public BadRequestException(String message, Throwable cause, boolean enableSuppression,
                             boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
