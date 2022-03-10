package com.thubas.petshelter.services;

import com.thubas.petshelter.models.User;
import com.thubas.petshelter.models.VerificationToken;

public interface VerificationTokenService {
	
	void deleteToken(String token);
	
	VerificationToken getToken(String token);
	
	VerificationToken createToken(User user);
	
	void verifyUser(VerificationToken verificationToken);
	
	boolean isValid(VerificationToken verificationToken); 


}
