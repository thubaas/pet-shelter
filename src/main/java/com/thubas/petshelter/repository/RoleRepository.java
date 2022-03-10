package com.thubas.petshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.petshelter.enums.ERole;
import com.thubas.petshelter.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(ERole name);

}
