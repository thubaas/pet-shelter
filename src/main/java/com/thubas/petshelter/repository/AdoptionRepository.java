package com.thubas.petshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.petshelter.models.Adoption;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

}
