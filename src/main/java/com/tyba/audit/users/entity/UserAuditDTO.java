package com.tyba.audit.users.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class UserAuditDTO {

  @JsonProperty
  private String userName;

  @JsonProperty
  private Action action;

  @JsonProperty
  private Date createdAt;

  public UserAuditDTO(final String userName, final Action action, final Date createdAt) {
    this.userName = userName;
    this.action = action;
    this.createdAt = createdAt;
  }

}