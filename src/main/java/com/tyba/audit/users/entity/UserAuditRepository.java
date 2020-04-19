package com.tyba.audit.users.entity;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository extends CrudRepository<UserAudit, Long> {

  @Override
  List<UserAudit> findAll();

}