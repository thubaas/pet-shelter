package com.thubas.petshelter.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.thubas.petshelter.dto.UserDto;
import com.thubas.petshelter.models.Address;
import com.thubas.petshelter.models.Role;
import com.thubas.petshelter.models.User;
import com.thubas.petshelter.repository.UserRepository;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
	
	@Autowired
	private UserRepository userRepository;
	
	@Mapping(target = "id", source = "user.id")
	@Mapping(target = "email", source = "user.email")
	@Mapping(target = "enabled", source = "user.enabled")
	@Mapping(target = "roles", expression = "java(getRoles(user.getRoles()))")
	@Mapping(target = "zipcode", expression = "java(user.getAddress().getZipcode())")
	@Mapping(target = "city", expression = "java(user.getAddress().getCity())")
	@Mapping(target = "street", expression = "java(user.getAddress().getStreet())")
	public abstract UserDto map(User user);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "email", source = "userDto.email")
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "enabled", source = "userDto.enabled")
	@Mapping(target = "address", expression = "java(getAddress(userDto))")
	@Mapping(target = "roles", expression = "java(getRoles(userDto.getEmail()))")
	public abstract User map(UserDto userDto);
	
	
	List<String> getRoles(Collection<Role> roles){
		List<String> mappedRoles = roles.stream()
				.map(role -> role.getName().name())
				.collect(Collectors.toList());
		return mappedRoles;
	}
	
	Collection<Role> getRoles(String email) {
		return userRepository.findByEmail(email).getRoles();
	}
	
	Address getAddress(UserDto userDto) {
		Address address = new Address();
		address.setZipcode(userDto.getZipcode());
		address.setStreet(userDto.getStreet());
		address.setCity(userDto.getCity());
		return address;
	}
	
	

}
