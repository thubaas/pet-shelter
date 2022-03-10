package com.thubas.petshelter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AppExceptionController {
	
	@ExceptionHandler(value = UserAccountLockedException.class)
	public ResponseEntity<Object> accountLocked(UserAccountLockedException exception) {

		return new ResponseEntity<Object> (
				"User account locked. Check your confirmation email.", 
				HttpStatus.FORBIDDEN
				);
		}
	
	@ExceptionHandler(value = UserEmailNotFoundException.class)
	public ResponseEntity<Object> emailNotfound(UserEmailNotFoundException exception) {

		return new ResponseEntity<Object> (
				"User not found. Check your credentials email.", 
				HttpStatus.FORBIDDEN
				);
		}

}
