package com.prads.csgoapi.exception;

public class BusinessException extends RuntimeException {
  private static final long serialVersionUID = -680833117758564205L;

  public BusinessException() {
    super();
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }
}
