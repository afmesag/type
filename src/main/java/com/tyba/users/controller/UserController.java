package com.tyba.users.controller;

import com.tyba.users.entity.dto.UserDTO;
import com.tyba.users.service.UserService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService service;

  public UserController(final UserService service) {
    this.service = service;
  }

  @PostMapping
  public void createUser(@Valid @RequestBody final UserDTO dto) {
    service.createUser(dto);
  }

}