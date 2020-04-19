package com.tyba.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApplicationException extends ResponseStatusException {

  private String code;

  public ApplicationException(
      final HttpStatus status,
      final String reason,
      final String code
  ) {
    super(status, reason);
    this.code = code;
  }

  public ApplicationException(
      final HttpStatus status,
      final String code
  ) {
    super(status);
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}