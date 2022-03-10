package com.thubas.petshelter.controllers;

import java.util.List;


import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thubas.petshelter.dto.PetDto;
import com.thubas.petshelter.services.PetService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pet-shelter/pets")
@RestController
public class PetController {
	
	private final PetService petService;
	
	@GetMapping
	public ResponseEntity<List<PetDto>> getPets(){
		return ResponseEntity.ok( petService.getPets());
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PetDto> savePet(@RequestBody @Valid PetDto petDto) {
		return ResponseEntity.ok(petService.addPet(petDto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PetDto> getPet(@PathVariable Long id) {
		return ResponseEntity.ok(petService.getPet(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PetDto> updatePet(@RequestBody @Valid PetDto petDto) {
		return ResponseEntity.ok(petService.addPet(petDto));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deletePet(@PathVariable Long id) {
		log.info("Pet to be deleted: {}", id);
		return ResponseEntity.ok(true);
		
	}

}
