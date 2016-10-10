package com.yyqian.imagine.exception;

/**
 * Created by yyqian on 12/7/15.
 * 500 Internal Server Error
 */
public class InternalServerErrorException extends RuntimeException {
  public InternalServerErrorException() {
  }

  public InternalServerErrorException(String message) {
    super(message);
  }

  public InternalServerErrorException(String message, Throwable cause) {
    super(message, cause);
  }

  public InternalServerErrorException(Throwable cause) {
    super(cause);
  }

  public InternalServerErrorException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
