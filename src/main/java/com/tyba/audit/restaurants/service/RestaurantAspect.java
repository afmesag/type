package com.tyba.audit.restaurants.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyba.audit.restaurants.entity.RestaurantAudit;
import com.tyba.audit.restaurants.entity.RestaurantAuditDTO;
import com.tyba.audit.restaurants.entity.RestaurantAuditRepository;
import com.tyba.auth.exception.UnauthorizedUserException;
import com.tyba.restaurants.entity.Restaurant;
import java.util.List;
import java.util.stream.Collectors;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestaurantAspect {

  private final RestaurantAuditRepository repository;

  private final ObjectMapper mapper;

  public RestaurantAspect(
      final RestaurantAuditRepository repository,
      final ObjectMapper mapper
  ) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * Save when get restaurants was successful
   *
   * @param lat Latitude
   * @param lng Longitude
   * @param restaurants Result list of restaurants
   */
  @AfterReturning(
      pointcut = "execution(* com.tyba.restaurants.controller.RestaurantController.getRestaurants(..)) && args(..,lat, lng)",
      returning = "restaurants",
      argNames = "lat, lng, restaurants"
  )
  public void getRestaurants(final double lat, final double lng, final Restaurant[] restaurants)
      throws JsonProcessingException {
    String payload = mapper.writeValueAsString(restaurants);
    RestaurantAudit audit = new RestaurantAudit(lat, lng, payload);
    repository.save(audit);
  }

  /**
   * Save when get restaurants was successful
   *
   * @param lat Latitude
   * @param lng Longitude
   * @param ex Exception thrown
   */
  @AfterThrowing(
      pointcut = "execution(* com.tyba.restaurants.controller.RestaurantController.getRestaurants(..)) && args(..,lat, lng)",
      throwing = "ex",
      argNames = "lat, lng, ex"
  )
  public void getRestaurantsAuthError(
      final double lat, final double lng,
      final UnauthorizedUserException ex
  ) {
    RestaurantAudit audit = new RestaurantAudit(lat, lng, ex.getMessage());
    repository.save(audit);
  }

  /**
   * Get all the restaurants audit
   *
   * @return List of restaurants audit DTO
   */
  public List<RestaurantAuditDTO> getAll() {
    return repository.findAll().stream()
        .map(audit -> new RestaurantAuditDTO(
            audit.getLat(), audit.getLng(),
            audit.getResult(), audit.getCreatedAt()
        ))
        .collect(Collectors.toList());
  }

}