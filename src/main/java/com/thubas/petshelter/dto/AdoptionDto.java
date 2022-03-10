package com.thubas.petshelter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptionDto {
	
	private Long id;
	private Long petId;
	private String petImageUrl;
	private String time;
	private String adoptionStatus;
	private String feedback;
	private Long userId;
	private String adopterAddress;

}
