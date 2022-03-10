package com.thubas.petshelter.services;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.thubas.petshelter.models.RefreshToken;
import com.thubas.petshelter.models.User;
import com.thubas.petshelter.repository.RefreshTokenRepository;
import com.thubas.petshelter.repository.UserRepository;
import com.thubas.petshelter.security.JwtConfig;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RefreshTokenService {
	
	private final JwtConfig jwtConfig;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	public RefreshToken findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(String email) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUser(userRepository.findByEmail(email));
		refreshToken.setExpiryDate(Instant.now().plusMillis(jwtConfig.getRefreshTokenExpirationMs()));
		refreshToken.setToken(UUID.randomUUID().toString());

		return refreshTokenRepository.save(refreshToken);
	} 

	public boolean isValid(RefreshToken token) {
		return (token.getExpiryDate().compareTo(Instant.now()) > 0);
	}

	@Transactional
	public void deleteByUserId(Long userId) {
		User user = userRepository.findById(userId).get();
		refreshTokenRepository.deleteByUser(user);
	}

}
