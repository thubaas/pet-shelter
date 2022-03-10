package com.thubas.petshelter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetDto {
	
	private Long id;
	private String name;
	private String description;
	private String imageUrl;
	private int age;
	private String gender;
	private String status;
	private String type;
	

}
  