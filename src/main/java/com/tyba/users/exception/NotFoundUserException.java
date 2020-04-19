package com.tyba.users.exception;

import com.tyba.handler.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundUserException extends ApplicationException {

  public NotFoundUserException(final String reason) {
    super(HttpStatus.NOT_FOUND, reason, "user.not-found");
  }

}