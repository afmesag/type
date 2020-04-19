package com.tyba.audit.users.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class UserAudit {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String userName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Action action;

  @CreationTimestamp
  private Date createdAt;

  public UserAudit() {
  }

  public UserAudit(final String userName, final Action action) {
    this.userName = userName;
    this.action = action;
  }

  public String getUserName() {
    return userName;
  }

  public Action getAction() {
    return action;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

}