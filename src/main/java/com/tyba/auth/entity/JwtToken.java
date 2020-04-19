package com.tyba.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtToken {

  @JsonProperty
  private String token;

  @JsonProperty
  private int validForMinutes;

  public JwtToken(final String token, final int validForMinutes) {
    this.token = token;
    this.validForMinutes = validForMinutes;
  }

  public String getToken() {
    return token;
  }

}