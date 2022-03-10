package com.thubas.petshelter.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thubas.petshelter.dto.AdoptionDto;
import com.thubas.petshelter.services.AdoptionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pet-shelter/adoptions")
@RestController
public class AdoptionController {
	
	private final AdoptionService adoptionService;
	
	
	@GetMapping
	public ResponseEntity<List<AdoptionDto>> getAdoptions() {
		List<AdoptionDto> adoptions = adoptionService.getAdoptions();
		return ResponseEntity.ok(adoptions);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AdoptionDto> getAdoption(@PathVariable Long id) {
		AdoptionDto adoption = adoptionService.getAdoption(id);
		return ResponseEntity.ok(adoption);
	}
	
	
	@PostMapping
	public ResponseEntity<AdoptionDto> createAdoption(@RequestBody @Valid AdoptionDto adoptionDto) {
		AdoptionDto adoption = adoptionService.createAdoption(adoptionDto);
		return ResponseEntity.ok(adoption);
	}
	
	@PutMapping
	public ResponseEntity<AdoptionDto> aproveAdoption(@RequestBody @Valid AdoptionDto adoptionDto) {
		
		AdoptionDto savedAdoptionDto = adoptionService.processAdoption(adoptionDto);
		return ResponseEntity.ok(savedAdoptionDto);
	}
	
	@DeleteMapping
	public ResponseEntity<Boolean> deleteAdoption(@PathVariable Long id) {
		
		adoptionService.deleteAdoption(id);
		return ResponseEntity.ok(true);
		
	}
	
	@GetMapping("/{id}/cancel")
	public ResponseEntity<AdoptionDto> cancelAdoption(@PathVariable Long id) {
		return ResponseEntity.ok(adoptionService.cancelAdoption(id));
		
	}

}
