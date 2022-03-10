package com.thubas.petshelter.services;

import com.thubas.petshelter.dto.SigninDto;
import com.thubas.petshelter.dto.SignupDto;
import com.thubas.petshelter.dto.UserDto;
import com.thubas.petshelter.models.User;

public interface AuthService {
	
	User signup(SignupDto signupDto);
	
	UserDto signin(SigninDto userDto);
	
	void signout();
	
	User getAuthenticatedUser();
	
	User getAuthenticatedUser(String email);
	
	boolean doesEmailExist(String email);
	
	boolean isAccountEnabled(String email);
	

}
