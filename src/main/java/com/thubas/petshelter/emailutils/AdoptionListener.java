package com.thubas.petshelter.emailutils;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AdoptionListener implements ApplicationListener<OnAdoptionProcessedEvent> {
	
	private final MailService mailService;
	
	@Override
	public void onApplicationEvent(OnAdoptionProcessedEvent event) {
		mailService.sendAdoptionResultEmail(event);
		
	}

}
