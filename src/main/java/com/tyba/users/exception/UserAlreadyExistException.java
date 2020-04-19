package com.tyba.users.exception;

import com.tyba.handler.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends ApplicationException {

  public UserAlreadyExistException(final String reason) {
    super(HttpStatus.CONFLICT, reason, "user.conflict");
  }

}
