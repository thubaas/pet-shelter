package com.thubas.petshelter.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thubas.petshelter.dto.SigninDto;
import com.thubas.petshelter.dto.SignupDto;
import com.thubas.petshelter.dto.UserDto;
import com.thubas.petshelter.enums.ERole;
import com.thubas.petshelter.exception.UserEmailNotFoundException;
import com.thubas.petshelter.mappers.UserMapper;
import com.thubas.petshelter.models.Address;
import com.thubas.petshelter.models.Role;
import com.thubas.petshelter.models.User;
import com.thubas.petshelter.repository.RoleRepository;
import com.thubas.petshelter.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authManager;
	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final RoleRepository roleRepository;
	private final UserMapper mapper;

	@Override
	public User signup(SignupDto signupDto) {
		
		User user = new User();
		user.setEmail(signupDto.getEmail());
		user.setPassword(encoder.encode(signupDto.getPassword()));
		
		Role role = roleRepository.findByName(ERole.ROLE_USER);
		user.getRoles().add(role);
		
		Address address = new Address();
		address.setZipcode(signupDto.getZipcode());
		address.setStreet(signupDto.getStreet());
		address.setCity(signupDto.getCity());
		user.setAddress(address);
		user.setEnabled(false);
		return userRepository.save(user);
	}

	@Override
	public UserDto signin(SigninDto signinDto) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						signinDto.getEmail(),  
						signinDto.getPassword())
				);
		log.error("Authentication: {}", authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User authenticatedUser = userRepository.findByEmail(signinDto.getEmail());
		return mapper.map(authenticatedUser);
	}

	@Override
	public void signout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	@Override
	public User getAuthenticatedUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("Authenticated Email");
		return userRepository.findByEmail(email);
	}

	@Override
	public User getAuthenticatedUser(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean doesEmailExist(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean isAccountEnabled(String email) {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			log.error("The user with that name could not be found");
			throw new UserEmailNotFoundException();
		}
		return user.getEnabled();
	}

}
