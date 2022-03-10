package com.thubas.petshelter.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thubas.petshelter.dto.PetDto;
import com.thubas.petshelter.enums.PetStatus;
import com.thubas.petshelter.mappers.PetMapper;
import com.thubas.petshelter.models.Pet;
import com.thubas.petshelter.repository.PetRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PetServiceImpl implements PetService {
	
	private final Cloudinary cloudinary;
	private final PetRepository petRepository;
	private final PetMapper mapper;

	@Override
	public List<PetDto> getPets() {
		List<Pet> pets = petRepository.findAll();
		List<PetDto> mappedPets = pets
				.stream()
				.map(pet -> mapper.map(pet))
				.collect(Collectors.toList());
		return mappedPets;
	}

	@Override
	public PetDto getPet(Long id) {
		Pet pet = petRepository.findById(id).get();
		return mapper.map(pet);
	}

	@Override
	public PetDto addPet(PetDto petDto) {
		petDto.setStatus(PetStatus.AVAILABLE.name());
		Pet pet = mapper.map(petDto);
		Pet savedPet = petRepository.save(pet);
		return mapper.map(savedPet);
	}

	@Override
	public void deletePet(Long id) {
		petRepository.deleteById(id);
	}

	@Override
	public PetDto addImage(Long petId, MultipartFile petImage) {
		
		try {
			File uploadFile = convertMultipartToFile(petImage);
			Map<?,?> uploadResult = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
			String imageUrl = uploadResult.get("url").toString();
			Pet pet = petRepository.findById(petId).get();
			pet.setImageUrl(imageUrl);
			Pet updatedPet = petRepository.save(pet);
			return mapper.map(updatedPet);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private File convertMultipartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();
		return convertedFile;
	}

}
