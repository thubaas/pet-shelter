package com.thubas.petshelter.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
	private Long id;
	private String email;
	private String zipcode;
	private String street;
	private String city;
	private String enabled;
	private List<String> roles = new ArrayList<>();
}
