package com.thubas.petshelter.services;

import java.util.List;

import com.thubas.petshelter.dto.AdoptionDto;

public interface AdoptionService {
	
	List<AdoptionDto> getAdoptions();
	
	AdoptionDto getAdoption(Long id);
	
	AdoptionDto createAdoption(AdoptionDto adoptionDto);
	
	AdoptionDto processAdoption(AdoptionDto adoptionDto);
	
	AdoptionDto cancelAdoption(Long id);
	
	void deleteAdoption(Long id);

}
