package com.tyba.audit.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tyba.audit.users.entity.Action;
import com.tyba.audit.users.entity.UserAudit;
import com.tyba.audit.users.entity.UserAuditRepository;
import com.tyba.auth.exception.UnauthorizedUserException;
import com.tyba.auth.service.AuthService;
import com.tyba.users.entity.dto.UserDTO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthAspect {

  private final AuthService service;

  private final UserAuditRepository repository;

  public AuthAspect(
      final AuthService service,
      final UserAuditRepository repository
  ) {
    this.service = service;
    this.repository = repository;
  }

  /**
   * Save when the login was successful
   *
   * @param dto User DTO
   */
  @AfterReturning(
      pointcut = "execution(* com.tyba.auth.controller.AuthController.login(..)) && args(dto)",
      argNames = "dto"
  )
  public void login(final UserDTO dto) {
    UserAudit audit = new UserAudit(dto.getUserName(), Action.LOGGED_IN);
    repository.save(audit);
  }

  /**
   * Save when the login wasn't successful
   *
   * @param dto User DTO
   * @param ex Exception thrown
   */
  @AfterThrowing(
      pointcut = "execution(* com.tyba.auth.controller.AuthController.login(..)) && args(dto)",
      throwing = "ex",
      argNames = "dto,ex"
  )
  public void loginFailed(final UserDTO dto, final UnauthorizedUserException ex) {
    UserAudit audit = new UserAudit(dto.getUserName(), Action.TRY_LOGIN);
    repository.save(audit);
  }

  /**
   * Save when the logout was successful
   *
   * @param token JWT Token
   */
  @AfterReturning(
      pointcut = "execution(* com.tyba.auth.controller.AuthController.logout(..)) && args(token)",
      argNames = "token"
  )
  public void logout(final String token) {
    DecodedJWT decodedToken = service.getDecodedToken(token);
    UserAudit audit = new UserAudit(
        decodedToken.getClaim("userName").asString(), Action.LOGGED_OUT
    );
    repository.save(audit);
  }

}