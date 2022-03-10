package com.thubas.petshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.petshelter.models.RefreshToken;
import com.thubas.petshelter.models.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	RefreshToken findByToken(String token);
	
	void deleteByUser(User user);

}
