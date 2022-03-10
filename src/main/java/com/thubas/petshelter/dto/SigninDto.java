package com.thubas.petshelter.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SigninDto {
	
	@Email
	private String email;
	
	@Min(8)
	@NotBlank(message = "Password cannot be blank")
	private String password;

}
