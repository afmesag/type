package com.tyba.users.service;

import com.tyba.hash.service.HashService;
import com.tyba.users.entity.Status;
import com.tyba.users.entity.User;
import com.tyba.users.entity.UserRepository;
import com.tyba.users.entity.dto.UserDTO;
import com.tyba.users.exception.NotFoundUserException;
import com.tyba.users.exception.UserAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private final HashService hashService;

  private final UserRepository repository;

  public UserService(
      final HashService hashService,
      final UserRepository repository
  ) {
    this.hashService = hashService;
    this.repository = repository;
  }

  /**
   * Saves a new user into the db with the hashed password
   *
   * @param dto User DTO data
   *
   * @throws UserAlreadyExistException if the given userName already exists
   */
  public void createUser(final UserDTO dto) {
    String userName = dto.getUserName();
    User user = findUserByUserName(userName);
    if (user != null) {
      String message = String.format("[%s]::USER ALREADY EXISTS", userName);
      LOGGER.error(message);
      throw new UserAlreadyExistException(message);
    }
    String password = dto.getPassword();
    String hashedPassword = hashService.hashStringSHA3_256(password);
    repository.save(new User(userName, hashedPassword));
  }

  /**
   * Login the given userName
   *
   * @param userName Username
   *
   * @throws NotFoundUserException if the given username wasn't found
   */
  public void login(final String userName) {
    User user = findUserByUserName(userName);
    user.setStatus(Status.LOGGED_IN);
    repository.save(user);
  }

  /**
   * Logout the given userName
   *
   * @param userName Username
   *
   * @throws NotFoundUserException if the given username wasn't found
   */
  public void logout(final String userName) {
    User user = findUserByUserName(userName);
    user.setStatus(Status.LOGGED_OUT);
    repository.save(user);
  }

  /**
   * Check if the given username and password matches with the store in the db
   *
   * @param userName       Username
   * @param hashedPassword Hashed password
   *
   * @return {@code true} if the passwords match, {@code false} otherwise
   *
   * @throws NotFoundUserException if the given username wasn't found
   */
  public boolean isValidUser(final String userName, final String hashedPassword) {
    User user = findUserByUserName(userName);
    return hashedPassword.equals(user.getPassword());
  }

  /**
   * Check if the given username is signed
   *
   * @param userName       Username
   * @param hashedPassword Hashed password
   *
   * @return {@code true} if the user is signed, {@code false} otherwise
   *
   * @throws NotFoundUserException if the given username wasn't found
   */
  public boolean isSigned(final String userName, final String hashedPassword) {
    User user = findUserByUserName(userName);
    return hashedPassword.equals(user.getPassword()) && Status.LOGGED_IN.equals(user.getStatus());
  }

  /**
   * Finds the user by its username
   *
   * @param userName Username
   *
   * @return User with the given username
   *
   * @throws NotFoundUserException if the given username wasn't found
   */
  private User findUserByUserName(final String userName) {
    return repository.findByUserName(userName)
        .orElseThrow(() -> {
          String message = String.format("[%s]::NOT FOUND USER", userName);
          LOGGER.error(message);
          return new NotFoundUserException(message);
        });
  }

}