package com.thubas.petshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.petshelter.models.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	
	void deleteByToken(String token);
	
	VerificationToken findByToken(String token);

}
