package com.thubas.petshelter.emailutils;

import lombok.Setter;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
@Setter
public class OnRegisteredEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private String appUrl;
	private String emailTo;
	private String EMAIL_FROM = "pet-shelter@mail.com";
	private String token;
	
	public OnRegisteredEvent(String appUrl, String emailTo, String token) {
		super(appUrl);
		this.appUrl = appUrl;
		this.emailTo = emailTo;
		this.token = token;
	}

}
