package com.tyba.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tyba.auth.entity.JwtToken;
import com.tyba.auth.exception.UnauthorizedUserException;
import com.tyba.hash.exception.HashException;
import com.tyba.hash.service.HashService;
import com.tyba.users.entity.dto.UserDTO;
import com.tyba.users.exception.NotFoundUserException;
import com.tyba.users.service.UserService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

  private static final String USER_NAME_CLAIM = "userName";

  private static final String HASH_CLAIM = "hash";

  private final UserService userService;

  private final HashService hashService;

  private final String secret;

  private final int expiresInMinutes;

  public AuthService(
      final UserService userService,
      final HashService hashService,
      @Value("${app.security.jwt.secret}") final String secret,
      @Value("${app.security.jwt.expire-minutes}") final int expiresInMinutes
  ) {
    this.userService = userService;
    this.hashService = hashService;
    this.secret = secret;
    this.expiresInMinutes = expiresInMinutes;
  }

  /**
   * Gets a JWT token that will expire in {@code expiresInMinutes}
   *
   * @param dto User DTO
   *
   * @return {@link JwtToken} with the generate JWT token
   *
   * @throws NotFoundUserException     if the username doesn't exits
   * @throws UnauthorizedUserException if the password is incorrect
   * @throws HashException             if there was an error during hashing
   */
  public JwtToken login(final UserDTO dto) {
    String userName = dto.getUserName();
    String password = dto.getPassword();
    String hashedPassword = hashService.hashStringSHA3_256(password);
    if (userService.isValidUser(userName, hashedPassword)) {
      userService.login(userName);
      String token = generateJWT(userName, hashedPassword);
      return new JwtToken(token, expiresInMinutes);
    } else {
      String message = String.format("[%s]::UNAUTHORIZED USER", userName);
      LOGGER.error(message);
      throw new UnauthorizedUserException(message);
    }
  }

  /**
   * Logout the user of the given token
   *
   * @param token JWT token
   *
   * @throws NotFoundUserException     if the username doesn't exits
   * @throws UnauthorizedUserException if the password is incorrect
   * @throws JWTVerificationException  if there're error with the JWT, such as it's expired
   */
  public void logout(final String token) {
    DecodedJWT decoded = getDecodedToken(token);
    String userName = decoded.getClaim(USER_NAME_CLAIM).asString();
    String hashedPassword = decoded.getClaim(HASH_CLAIM).asString();
    if (userService.isValidUser(userName, hashedPassword)) {
      userService.logout(userName);
    } else {
      String message = String.format("[%s]::UNAUTHORIZED USER", userName);
      LOGGER.error(message);
      throw new UnauthorizedUserException(message);
    }
  }

  /**
   * Check if the given token is valid
   *
   * @param token JWT token
   *
   * @throws NotFoundUserException     if the username doesn't exits
   * @throws UnauthorizedUserException if the password is incorrect
   * @throws JWTVerificationException  if there're error with the JWT, such as it's expired
   */
  public void checkValidToken(final String token) {
    DecodedJWT decoded = getDecodedToken(token);
    String userName = decoded.getClaim(USER_NAME_CLAIM).asString();
    String hashedPassword = decoded.getClaim(HASH_CLAIM).asString();
    if (!userService.isSigned(userName, hashedPassword)) {
      String message = String.format("[%s]::UNAUTHORIZED USER", userName);
      LOGGER.error(message);
      throw new UnauthorizedUserException(message);
    }
  }

  /**
   * Generates a JWT token that expires in {@code expiresInMinutes}, this token could be refreshed
   * in each request depending on the "refresh" claim.
   *
   * @param userName       User name
   * @param hashedPassword Hashed password
   *
   * @return JWT token signed with the {@code secret}
   */
  private String generateJWT(final String userName, final String hashedPassword) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    Date expireAt = Date.from(Instant.now().plus(expiresInMinutes, ChronoUnit.MINUTES));
    return JWT.create()
        .withExpiresAt(expireAt)
        .withClaim(USER_NAME_CLAIM, userName)
        .withClaim(HASH_CLAIM, hashedPassword)
        .sign(algorithm);
  }

  /**
   * Get the decoded JWT token
   *
   * @param token JWT token
   *
   * @return Decoded JWT token
   *
   * @throws UnauthorizedUserException if there're some missing claims
   * @throws JWTVerificationException  if there're error with the JWT, such as it's expired
   */
  public DecodedJWT getDecodedToken(final String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT decoded = verifier.verify(token);
      if (decoded.getClaim(USER_NAME_CLAIM).isNull()) {
        throw new UnauthorizedUserException("NOT FOUND USER NAME CLAIM");
      }
      if (decoded.getClaim(HASH_CLAIM).isNull()) {
        throw new UnauthorizedUserException("NOT FOUND HASH CLAIM");
      }
      return decoded;
    } catch (JWTVerificationException | UnauthorizedUserException e) {
      LOGGER.error(e.getMessage());
      throw new UnauthorizedUserException();
    }
  }

}