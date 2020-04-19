package com.tyba.restaurants.service;

import com.tyba.restaurants.entity.Restaurant;
import com.tyba.restaurants.exception.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestaurantService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

  private final RestTemplate restTemplate;

  private final String restaurantsUrl;

  public RestaurantService(
      final RestTemplate restTemplate,
      @Value("${app.restaurants.url}") final String restaurantsUrl
  ) {
    this.restTemplate = restTemplate;
    this.restaurantsUrl = restaurantsUrl;
  }

  /**
   * Find the restaurants close from the given latitude and longitude
   *
   * @param lat Latitude
   * @param lng Longitude
   *
   * @return List of {@link Restaurant}
   * @throws RequestException if there were a 4XX or 5XX error
   */
  public Restaurant[] findByCoordinates(final double lat, final double lng) {
    // TODO Sets a real restaurant service and transform that format into the {@link Restaurant} format
    try {
      return restTemplate.getForObject(restaurantsUrl, Restaurant[].class);
    } catch (HttpStatusCodeException e) {
      String message = String.format("[%f,%f]::ERROR DURING REQUEST RESTAURANTS", lat, lng);
      LOGGER.error(message);
      throw new RequestException(message);
    }
  }

}