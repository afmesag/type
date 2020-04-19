package com.tyba.audit.restaurants.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class RestaurantAuditDTO {

  @JsonProperty
  private double lat;

  @JsonProperty
  private double lng;

  @JsonProperty
  private String result;

  @JsonProperty
  private Date createdAt;

  public RestaurantAuditDTO(
      final double lat, final double lng,
      final String result, final Date createdAt
  ) {
    this.lat = lat;
    this.lng = lng;
    this.result = result;
    this.createdAt = createdAt;
  }

}