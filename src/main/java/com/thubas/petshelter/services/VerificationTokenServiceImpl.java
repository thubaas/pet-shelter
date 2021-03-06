package com.thubas.petshelter.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.thubas.petshelter.models.User;
import com.thubas.petshelter.models.VerificationToken;
import com.thubas.petshelter.repository.UserRepository;
import com.thubas.petshelter.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
	
	private final VerificationTokenRepository verificationTokenRepository;
	private final UserRepository userRepository;
	
	@Override
	public void deleteToken(String token) {
		verificationTokenRepository.deleteByToken(token);
		
	}

	@Override
	public VerificationToken createToken(User user) {
		final Long EXPIRATION_TIME = Instant.now().plusSeconds(604800).toEpochMilli();
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setExpirationTime(EXPIRATION_TIME);
		verificationToken.setToken(token);
		User savedUser = userRepository.findByEmail(user.getEmail());
		verificationToken.setUser(savedUser);
		return verificationTokenRepository.save(verificationToken);
	}

	@Override
	public VerificationToken getToken(String token) {
		log.info("Verification Token: {}", token);
		return verificationTokenRepository.findByToken(token);
		
	}

	@Override
	public void verifyUser(VerificationToken verificationToken) {
		log.info("Starting Verification");
		String email = verificationToken.getUser().getEmail();
		User user = userRepository.findByEmail(email);
		user.setEnabled(true);
		userRepository.save(user);
		deleteToken(verificationToken.getToken());
		log.info("Token Verified");
	}
	
	@Override
	public boolean isValid(VerificationToken verificationToken) {
		Instant tokenExpiration = Instant.ofEpochMilli(verificationToken.getExpirationTime());
		return (tokenExpiration.compareTo(Instant.now()) > 0);
//		return (tokenExpiration.compareTo(Instant.now()) < 0);
	}

}
