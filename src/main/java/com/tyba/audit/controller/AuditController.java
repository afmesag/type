package com.tyba.audit.controller;

import com.tyba.audit.restaurants.service.RestaurantAspect;
import com.tyba.audit.users.service.UserAspect;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audits")
public class AuditController {

  private final RestaurantAspect restaurantAspect;

  private final UserAspect userAspect;

  public AuditController(
      final RestaurantAspect restaurantAspect,
      final UserAspect userAspect
  ) {
    this.restaurantAspect = restaurantAspect;
    this.userAspect = userAspect;
  }

  @GetMapping
  public Map<String, Object> getAllAudits() {
    return Map.of(
        "UserAudit", userAspect.getAll(),
        "RestaurantAudit", restaurantAspect.getAll()
    );
  }

}