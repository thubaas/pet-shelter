package com.thubas.petshelter.controllers;

import static java.time.LocalDateTime.now;

import java.time.Instant;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thubas.petshelter.dto.AuthDto;
import com.thubas.petshelter.dto.RefreshTokenRequest;
import com.thubas.petshelter.dto.RefreshTokenResponse;
import com.thubas.petshelter.dto.SigninDto;
import com.thubas.petshelter.dto.SignupDto;
import com.thubas.petshelter.dto.UserDto;
import com.thubas.petshelter.emailutils.OnRegisteredEvent;
import com.thubas.petshelter.exception.UserAccountLockedException;
import com.thubas.petshelter.mappers.UserMapper;
import com.thubas.petshelter.models.RefreshToken;
import com.thubas.petshelter.models.Response;
import com.thubas.petshelter.models.User;
import com.thubas.petshelter.models.VerificationToken;
import com.thubas.petshelter.security.JwtConfig;
import com.thubas.petshelter.security.JwtUtils;
import com.thubas.petshelter.services.AuthService;
import com.thubas.petshelter.services.RefreshTokenService;
import com.thubas.petshelter.services.VerificationTokenService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pet-shelter/auth")
@RestController
public class AuthController {
	
	private final AuthService authService;
	private final VerificationTokenService verificationTokenService;
	private final ApplicationEventPublisher eventPublisher;
	private final RefreshTokenService refreshTokenService;
	private final JwtUtils jwtUtils;
	private final UserMapper userMapper;
	private final JwtConfig jwtConfig;
	
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody @Valid SignupDto signupDto) {
		
//		if(authService.doesEmailExist(signupDto.getEmail())) {
//			return ResponseEntity.badRequest()
//					.body(
//							Response.builder()
//							.timeStamp(now())
//							.message("Error: Email already in use")
//							.status(HttpStatus.BAD_REQUEST)
//							.statusCode(HttpStatus.BAD_REQUEST.value())
//							.build()
//						);
//		}
		
		User registeredUser = authService.signup(signupDto);
		String appUrl = "http://localhost:8080/pet-shelter/auth/confirm-registration/?token=";
		String verificationToken = verificationTokenService.createToken(registeredUser).getToken();
		
		eventPublisher.publishEvent(
				new OnRegisteredEvent(appUrl, registeredUser.getEmail(), verificationToken)
				);
		return ResponseEntity.ok("User registered successfully");
		
	}
	
	@Transactional
	@GetMapping("/confirm-registration")
	public ResponseEntity<String> confirmSignup(@RequestParam String token) {
		log.info("Confirmation Token Running");
		VerificationToken verificationToken = verificationTokenService.getToken(token);
		
//		if(!verificationTokenService.isValid(verificationToken)) {
//			verificationTokenService.deleteToken(verificationToken.getToken());
//			User registeredUser = verificationToken.getUser();
//			String appUrl = "http://localhost:8080/pet-shelter/auth/confirm-registration/?token=";
//			String newVerificationToken = verificationTokenService.createToken(registeredUser).getToken();
//			
//			eventPublisher.publishEvent(new OnRegisteredEvent(
//					appUrl, 
//					registeredUser.getEmail(), 
//					newVerificationToken));
//			
//			return ResponseEntity.ok(
//					Response.builder()
//					.timeStamp(now())
//					.message("Your confirmation linked has expired. Please check your email for a new confirmation link.")
//					.status(HttpStatus.FORBIDDEN)
//					.statusCode(HttpStatus.FORBIDDEN.value())
//					.build()
//					);
//			
//		}
		
		verificationTokenService.verifyUser(verificationToken);
		return ResponseEntity.ok("Account Confirmed Successfully");
		
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthDto> authenticateUser(@RequestBody @Valid SigninDto request) {
		
		if(!authService.isAccountEnabled(request.getEmail())) {
			throw new UserAccountLockedException();		
		}

		UserDto userDto = authService.signin(request);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDto.getEmail());
		String accessToken = jwtUtils.generateToken(userDto.getEmail());
		Long tokenExpiration = Instant.now().plusMillis(jwtConfig.getTokenExpirationMs()).toEpochMilli();	
		
		AuthDto authRes = new AuthDto(userDto, accessToken, refreshToken.getToken(), tokenExpiration);
	
		return ResponseEntity.ok(authRes);

	}
	
	@PostMapping("/refresh-token")
	public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
		
		RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken());
		User user = refreshToken.getUser();
//		if(!refreshTokenService.isValid(refreshToken)) {
//			refreshTokenService.deleteByUserId(user.getId());
//			return ResponseEntity.ok(
//					Response.builder()
//					.timeStamp(now())
//					.message("Refresh token expired please signin again")
//					.status(HttpStatus.FORBIDDEN)
//					.statusCode(HttpStatus.FORBIDDEN.value())
//					.build()
//					);
			
//			throw exception 
//		}
		
		String accessToken = jwtUtils.generateToken(user.getEmail());
		Long tokenExpiration = Instant.now().plusMillis(jwtConfig.getTokenExpirationMs()).toEpochMilli();

		RefreshTokenResponse refreshRes = new RefreshTokenResponse(accessToken, tokenExpiration);
		return ResponseEntity.ok(refreshRes);
	}

	@PostMapping("/signout")
	public ResponseEntity<String> logoutUser() {
		authService.signout();
		return ResponseEntity.ok("You have successfully signed out");
	}



}
