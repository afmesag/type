package com.tyba.restaurants.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Restaurant {

  @JsonProperty
  private String id;

  @JsonProperty
  private String name;

  @JsonProperty
  private List<Schedule> schedules;

}