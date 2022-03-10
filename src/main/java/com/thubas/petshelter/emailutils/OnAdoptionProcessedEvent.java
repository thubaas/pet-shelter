package com.thubas.petshelter.emailutils;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnAdoptionProcessedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private String appUrl;
	private String emailTo;
	private String status;
	private final String EMAIL_FROM = "pet-shelter@mail.com";
	
	public OnAdoptionProcessedEvent(String appUrl, String emailTo, String status) {
		super(appUrl);
		this.appUrl = appUrl;
		this.emailTo = emailTo;
		this.status = status;
	}

}
