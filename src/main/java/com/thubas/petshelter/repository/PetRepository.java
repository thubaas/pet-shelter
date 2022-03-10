package com.thubas.petshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.petshelter.models.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
