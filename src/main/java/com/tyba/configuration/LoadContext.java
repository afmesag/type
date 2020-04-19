package com.tyba.configuration;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LoadContext {

  private final int timeout;

  public LoadContext(@Value("${app.request.timeout}") final int timeout) {
    this.timeout = timeout;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder()
        .setConnectTimeout(Duration.ofMillis(timeout))
        .setReadTimeout(Duration.ofMillis(timeout))
        .build();
  }

}