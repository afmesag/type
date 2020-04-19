package com.tyba.audit.restaurants.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class RestaurantAudit {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private double lat;

  @Column(nullable = false)
  private double lng;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String result;

  @CreationTimestamp
  private Date createdAt;

  public RestaurantAudit() {
  }

  public RestaurantAudit(final double lat, final double lng, final String result) {
    this.lat = lat;
    this.lng = lng;
    this.result = result;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public String getResult() {
    return result;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

}