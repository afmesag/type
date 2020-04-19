package com.tyba.audit.restaurants.entity;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantAuditRepository extends CrudRepository<RestaurantAudit, Long> {

  @Override
  List<RestaurantAudit> findAll();

}