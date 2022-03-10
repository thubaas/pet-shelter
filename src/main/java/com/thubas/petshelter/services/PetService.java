package com.thubas.petshelter.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.thubas.petshelter.dto.PetDto;

public interface PetService {
	
	List<PetDto> getPets();
	
	PetDto getPet(Long id);
	
	PetDto addPet(PetDto petDto);
	
	void deletePet(Long id);
	
	PetDto addImage(Long petId, MultipartFile petImage);
	
}
