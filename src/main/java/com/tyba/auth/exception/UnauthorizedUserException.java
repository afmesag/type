package com.tyba.auth.exception;

import com.tyba.handler.ApplicationException;
import org.springframework.http.HttpStatus;

public class UnauthorizedUserException extends ApplicationException {

  public UnauthorizedUserException(final String reason) {
    super(HttpStatus.UNAUTHORIZED, reason, "auth.unauthorized");
  }

  public UnauthorizedUserException() {
    super(HttpStatus.UNAUTHORIZED, "auth.unauthorized");
  }

}