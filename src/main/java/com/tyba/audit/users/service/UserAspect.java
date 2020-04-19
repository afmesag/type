package com.tyba.audit.users.service;

import com.tyba.audit.users.entity.Action;
import com.tyba.audit.users.entity.UserAudit;
import com.tyba.audit.users.entity.UserAuditDTO;
import com.tyba.audit.users.entity.UserAuditRepository;
import com.tyba.users.entity.dto.UserDTO;
import com.tyba.users.exception.UserAlreadyExistException;
import java.util.List;
import java.util.stream.Collectors;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {

  private final UserAuditRepository repository;

  public UserAspect(final UserAuditRepository repository) {
    this.repository = repository;
  }

  /**
   * Save when the user creation was successful
   *
   * @param dto User DTO
   */
  @AfterReturning(
      pointcut = "execution(* com.tyba.users.controller.UserController.createUser(..)) && args(dto)",
      argNames = "dto"
  )
  public void userCreation(final UserDTO dto) {
    UserAudit audit = new UserAudit(dto.getUserName(), Action.CREATED);
    repository.save(audit);
  }

  /**
   * Saves when the user creation wasn't successful due the user already exists
   *
   * @param dto     User DTO
   * @param ignored Ignored exception
   */
  @AfterThrowing(
      pointcut = "execution(* com.tyba.users.controller.UserController.createUser(..)) && args(dto)",
      throwing = "ignored",
      argNames = "dto,ignored"
  )
  public void userCreationFailed(final UserDTO dto, final UserAlreadyExistException ignored) {
    UserAudit audit = new UserAudit(dto.getUserName(), Action.ALREADY_EXITS);
    repository.save(audit);
  }

  /**
   * Get all the user audits
   *
   * @return List of user audits DTO
   */
  public List<UserAuditDTO> getAll() {
    return repository.findAll().stream()
        .map(audit -> new UserAuditDTO(
            audit.getUserName(), audit.getAction(), audit.getCreatedAt()
        ))
        .collect(Collectors.toList());
  }

}