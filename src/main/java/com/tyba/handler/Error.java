package com.tyba.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class Error {

  @JsonProperty
  private HttpStatus status;

  @JsonProperty
  private String message;

  @JsonProperty
  private String code;

  public Error(final HttpStatus status, final String message, final String code) {
    this.message = message;
    this.code = code;
    this.status = status;
  }

}