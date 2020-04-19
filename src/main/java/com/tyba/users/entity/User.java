package com.tyba.users.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "\"user\"")
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false)
  private String userName;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  private Status status;

  @CreationTimestamp
  @Column(updatable = false)
  private Date createdAt;

  @UpdateTimestamp
  private Date updatedAt;

  public User() {
  }

  public User(final String userName, final String password) {
    this.userName = userName;
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(final Status status) {
    this.status = status;
  }

}