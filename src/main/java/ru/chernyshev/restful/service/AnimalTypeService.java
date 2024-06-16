package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.AnimalType;
import ru.chernyshev.restful.dto.AnimalTypeDto;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.AnimalTypeMapper;
import ru.chernyshev.restful.repository.AnimalTypeRepository;

@Service
public class AnimalTypeService {
    @Autowired
    private AnimalTypeRepository animalTypeRepository;
    @Autowired
    private AnimalTypeMapper animalTypeMapper;


    public AnimalTypeDto createAnimalType(AnimalTypeDto animalTypeDto) {

        AnimalType animalType = new AnimalType();

        animalType.setType(animalTypeDto.getType());

        animalTypeRepository.save(animalType);

        return animalTypeMapper.toDto(animalType);
    }

    public AnimalTypeDto getAnimalType(Long id) {
        return animalTypeRepository.findById(id)
                .map(animalTypeMapper:: toDto)
                .orElseThrow(() -> new NotFoundException("Animal type with this id has not found: " + id));
    }

    public AnimalTypeDto setAnimalType(Long id, AnimalTypeDto dto) {
        AnimalType animalType = animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal type with this id has not found: " + id));

        animalType.setType(dto.getType());

        animalTypeRepository.save(animalType);

        return animalTypeMapper.toDto(animalType);
    }

    public void deleteAnimalType(Long id) {
        AnimalType animalType = animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal type with this id has not found: " + id));

        animalTypeRepository.delete(animalType);
    }
}
