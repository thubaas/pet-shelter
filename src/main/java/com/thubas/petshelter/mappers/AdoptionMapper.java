package com.thubas.petshelter.mappers;

import java.time.Instant;
import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ocpsoft.prettytime.PrettyTime;

import com.thubas.petshelter.dto.AdoptionDto;
import com.thubas.petshelter.models.Adoption;
import com.thubas.petshelter.models.Pet;
import com.thubas.petshelter.models.User;

@Mapper(componentModel = "spring")
public abstract class AdoptionMapper {
	
	private PrettyTime p = new PrettyTime();
	
	@Mapping(target = "id", source = "adoption.id")
	@Mapping(target = "petId", expression = "java(adoption.getPet().getId())")
	@Mapping(target = "petImageUrl", expression = "java(adoption.getPet().getImageUrl())")
	@Mapping(target = "adoptionStatus", source = "adoption.status")
	@Mapping(target = "time", expression = "java(createPrettyTime(adoption.getTime()))")
	@Mapping(target = "feedback", source = "adoption.feedback")
	@Mapping(target = "userId", expression = "java(adoption.getUser().getId())")
	@Mapping(target = "adopterAddress", expression = "java(adoption.getUser().getAddress().toString())")
	public abstract AdoptionDto map(Adoption adoption);
	
	@Mapping(target = "id",  ignore = true)
	@Mapping(target = "pet", source = "pet")
	@Mapping(target = "status", source = "adoptionDto.adoptionStatus")
	@Mapping(target = "time", ignore = true)
	@Mapping(target = "feedback", source = "adoptionDto.feedback")
	@Mapping(target = "user", source = "user")
	public abstract Adoption map(AdoptionDto adoptionDto, User user, Pet pet);
	
	
	protected String createPrettyTime(Instant instant) {
		return p.format(Date.from(instant));
	}
	
}
