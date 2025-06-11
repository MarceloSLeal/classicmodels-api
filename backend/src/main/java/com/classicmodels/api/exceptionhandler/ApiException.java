package com.classicmodels.api.exceptionhandler;

public class ApiException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ApiException(String message) {
    super(message);
  }
}
