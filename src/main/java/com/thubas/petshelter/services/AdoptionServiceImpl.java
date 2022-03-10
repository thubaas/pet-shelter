package com.thubas.petshelter.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.thubas.petshelter.dto.AdoptionDto;
import com.thubas.petshelter.emailutils.OnAdoptionProcessedEvent;
import com.thubas.petshelter.enums.AdoptionStatus;
import com.thubas.petshelter.enums.PetStatus;
import com.thubas.petshelter.mappers.AdoptionMapper;
import com.thubas.petshelter.models.Adoption;
import com.thubas.petshelter.models.Pet;
import com.thubas.petshelter.models.User;
import com.thubas.petshelter.repository.AdoptionRepository;
import com.thubas.petshelter.repository.PetRepository;
import com.thubas.petshelter.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdoptionServiceImpl implements AdoptionService {
	
	private final AdoptionRepository adoptionRepository;
	private final AdoptionMapper mapper;
	private final UserRepository userRepository;
	private final PetRepository petRepository;
	private final ApplicationEventPublisher eventPublisher;
	private final AuthService authService;
	private final String appUrl = "";

	@Override
	public List<AdoptionDto> getAdoptions() {
		List<Adoption> adoptions = adoptionRepository.findAll();
		return adoptions.stream()
				.map(adoption -> mapper.map(adoption))
				.collect(Collectors.toList());
	}

	@Override
	public AdoptionDto getAdoption(Long id) {
		Adoption adoption = adoptionRepository.findById(id).get();
		return mapper.map(adoption);
	}

	@Override
	public AdoptionDto createAdoption(AdoptionDto adoptionDto) {
		Pet pet = petRepository.findById(adoptionDto.getPetId()).get();
		if(!pet.getStatus().equals(PetStatus.AVAILABLE)) {
			throw new IllegalStateException("This Pet is not available for adoption");
		}
		
//		adoptionDto.setTime(LocalDateTime.now());
		User adopter = authService.getAuthenticatedUser();
		pet.setStatus(PetStatus.RESERVED);
		adoptionDto.setAdoptionStatus(AdoptionStatus.PENDING.name());
		Adoption adoption = mapper.map(adoptionDto, adopter, pet);
		adoption.setTime(Instant.now());
		Adoption savedAdoption = adoptionRepository.save(adoption);
		eventPublisher.publishEvent(
				new OnAdoptionProcessedEvent(appUrl, adopter.getEmail(), AdoptionStatus.PENDING.name())
				);
		return mapper.map(savedAdoption);
	}
	
	@Override
	public AdoptionDto processAdoption(AdoptionDto adoptionDto) {
		
		if(adoptionDto.getAdoptionStatus().equals(AdoptionStatus.DENIED.name())) {
			return denyAdoption(adoptionDto.getId());
		}
		
		if(adoptionDto.getAdoptionStatus().equals(AdoptionStatus.APROVED.name())) {
			return aproveAdoption(adoptionDto.getId());
		}

		return null;
	}


	@Override
	public void deleteAdoption(Long id) {
		adoptionRepository.deleteById(id);
	}

	@Override
	public AdoptionDto cancelAdoption(Long id) {
		Adoption adoption = adoptionRepository.findById(id).get();
		adoption.setStatus(AdoptionStatus.CANCELLED);
		Pet pet = adoption.getPet();
		pet.setStatus(PetStatus.AVAILABLE);
		petRepository.save(pet);
		eventPublisher.publishEvent(
				new OnAdoptionProcessedEvent(
						appUrl, 
						adoption.getUser().getEmail(), 
						AdoptionStatus.CANCELLED.name()
						)
				);
		Adoption processedAdoption = adoptionRepository.save(adoption);
		return mapper.map(processedAdoption);
	}
	
	

	private AdoptionDto aproveAdoption(Long id) {
		Adoption adoption = adoptionRepository.findById(id).get();
		adoption.setStatus(AdoptionStatus.APROVED);
		Pet pet = adoption.getPet();
		pet.setStatus(PetStatus.ADOPTED);
		petRepository.save(pet);
		eventPublisher.publishEvent(
				new OnAdoptionProcessedEvent(
						appUrl, 
						adoption.getUser().getEmail(), 
						AdoptionStatus.APROVED.name()
						)
				);
		Adoption processedAdoption = adoptionRepository.save(adoption);
		return mapper.map(processedAdoption);
	}

	
	private AdoptionDto denyAdoption(Long id) {
		Adoption adoption = adoptionRepository.findById(id).get();
		adoption.setStatus(AdoptionStatus.DENIED);
		Pet pet = adoption.getPet();
		pet.setStatus(PetStatus.AVAILABLE);
		petRepository.save(pet);
		eventPublisher.publishEvent(
				new OnAdoptionProcessedEvent(
						appUrl, 
						adoption.getUser().getEmail(), 
						AdoptionStatus.DENIED.name()
						)
				);
		Adoption processedAdoption = adoptionRepository.save(adoption);
		return mapper.map(processedAdoption);
	}


}
