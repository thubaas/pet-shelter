package com.thubas.petshelter.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thubas.petshelter.dto.PetDto;
import com.thubas.petshelter.models.Pet;

@Mapper(componentModel = "spring")
public abstract class PetMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "name", source = "petDto.name")
	@Mapping(target = "age", source = "petDto.age")
	@Mapping(target = "gender", source = "petDto.gender")
	@Mapping(target = "description", source = "petDto.description")
	@Mapping(target = "imageUrl", source = "petDto.imageUrl")
	@Mapping(target = "type", source = "petDto.type")
	@Mapping(target = "status", source = "petDto.status")
	public abstract Pet map(PetDto petDto);
	
	@Mapping(target = "id", source = "pet.id")
	@Mapping(target = "name", source = "pet.name") 
	@Mapping(target = "age", source = "pet.age")
	@Mapping(target = "gender", source = "pet.gender")
	@Mapping(target = "description", source = "pet.description")
	@Mapping(target = "imageUrl", source = "pet.imageUrl")
	@Mapping(target = "type", source = "pet.type")
	@Mapping(target = "status", source = "pet.status")
	public abstract PetDto map(Pet pet);

}
