package com.thubas.petshelter.services;

import com.thubas.petshelter.dto.AdoptionDto;

public interface UserService {
	
	AdoptionDto adoptPet(AdoptionDto adoptionDto);
	
	void cancelAdoption(Long adoptionId);
	
	AdoptionDto processAdoption(Long id);
	
	
}
