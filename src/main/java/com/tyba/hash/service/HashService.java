package com.tyba.hash.service;

import com.tyba.hash.exception.HashException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HashService {

  private static final Logger LOGGER = LoggerFactory.getLogger(HashService.class);

  /**
   * Hash the given string using the {@code SHA3-256} algorithm
   *
   * @param s String to be hashed
   *
   * @return Hashed string
   *
   * @throws HashException if the algorithm couldn't be found
   */
  public String hashStringSHA3_256(final String s) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA3-256");
      byte[] bytes = digest.digest(s.getBytes(StandardCharsets.UTF_8));
      StringBuilder builder = new StringBuilder();
      for (byte b : bytes) {
        builder.append(String.format("%02x", b & 0xff));
      }
      return builder.toString();
    } catch (NoSuchAlgorithmException e) {
      String message = "ERROR DURING HASHING";
      LOGGER.error(message);
      throw new HashException(message);
    }
  }

}