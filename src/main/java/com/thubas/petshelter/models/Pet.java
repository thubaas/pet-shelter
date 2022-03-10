package com.thubas.petshelter.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.thubas.petshelter.enums.Gender;
import com.thubas.petshelter.enums.PetStatus;
import com.thubas.petshelter.enums.PetType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	private String name;
	private String description;
	private int age;
	private String imageUrl;
	
	@Enumerated(value = EnumType.STRING)
	private Gender gender;
	@Enumerated(value = EnumType.STRING)
	private PetStatus status;
	@Enumerated(value = EnumType.STRING)
	private PetType type;

}
