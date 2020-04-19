package com.tyba.auth.controller;

import com.tyba.auth.entity.JwtToken;
import com.tyba.auth.service.AuthService;
import com.tyba.users.entity.dto.UserDTO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService service;

  public AuthController(final AuthService service) {
    this.service = service;
  }

  @PostMapping("/login")
  public JwtToken login(@Valid @RequestBody final UserDTO dto) {
    return service.login(dto);
  }

  @PostMapping("/logout")
  public void logout(@RequestHeader("${app.security.jwt.header}") final String token) {
    service.logout(token);
  }

}