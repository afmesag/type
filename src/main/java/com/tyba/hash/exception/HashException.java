package com.tyba.hash.exception;

import com.tyba.handler.ApplicationException;
import org.springframework.http.HttpStatus;

public class HashException extends ApplicationException {


  public HashException(final String reason) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, reason, "hash.error");
  }

}