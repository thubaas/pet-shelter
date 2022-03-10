package com.thubas.petshelter.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
	
	private String secretKey;
	private String tokenPrefix;
	private Integer tokenExpirationMs;
	private Integer refreshTokenExpirationMs;

}
