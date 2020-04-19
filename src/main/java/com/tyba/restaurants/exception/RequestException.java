package com.tyba.restaurants.exception;

import com.tyba.handler.ApplicationException;
import org.springframework.http.HttpStatus;

public class RequestException extends ApplicationException {

  public RequestException(final String reason) {
    super(HttpStatus.BAD_GATEWAY, reason, "restaurant.request.error");
  }

}