package com.tyba.restaurants.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.DayOfWeek;
import java.util.Date;

public class Schedule {

  @JsonProperty
  private DayOfWeek day;

  @JsonProperty
  private Date openTime;

  @JsonProperty
  private Date closeTime;

}