package com.yyqian.imagine.exception;

/**
 * Created by yyqian on 12/7/15.
 * 404 Not Found
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException() {
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundException(Throwable cause) {
    super(cause);
  }

  public NotFoundException(String message, Throwable cause, boolean enableSuppression,
                           boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
