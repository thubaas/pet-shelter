package com.thubas.petshelter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthDto {

	private UserDto user;
	private String token;
	private String refreshToken;
	private Long tokenExpiration;
}
