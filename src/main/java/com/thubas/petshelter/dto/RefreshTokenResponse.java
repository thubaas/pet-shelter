package com.thubas.petshelter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefreshTokenResponse {
	
	private String token;
	private Long tokenExpiration;
}
