package com.thubas.petshelter.bootstrap;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.thubas.petshelter.enums.ERole;
import com.thubas.petshelter.models.Role;
import com.thubas.petshelter.repository.RoleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	private final RoleRepository roleRepository;
	
	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if(roleRepository.count() == 0) {
			roleRepository.save(new Role(null, ERole.ROLE_USER));
			roleRepository.save(new Role(null, ERole.ROLE_ADMIN));
		}
		
	}

}
