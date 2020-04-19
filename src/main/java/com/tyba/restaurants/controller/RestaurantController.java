package com.tyba.restaurants.controller;

import com.tyba.auth.service.AuthService;
import com.tyba.restaurants.entity.Restaurant;
import com.tyba.restaurants.service.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

  private final AuthService authService;

  private final RestaurantService service;


  public RestaurantController(
      final AuthService authService,
      final RestaurantService service
  ) {
    this.authService = authService;
    this.service = service;
  }

  @GetMapping
  public Restaurant[] getRestaurants(
      @RequestHeader("${app.security.jwt.header}") final String token,
      @RequestParam("lat") final double lat,
      @RequestParam("lng") final double lng
  ) {
    authService.checkValidToken(token);
    return service.findByCoordinates(lat, lng);
  }

}