package com.tyba.users.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

@JsonInclude(Include.NON_NULL)
public class UserDTO {

  @JsonProperty
  @NotEmpty
  private String userName;

  @JsonProperty
  @NotEmpty
  private String password;

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

}