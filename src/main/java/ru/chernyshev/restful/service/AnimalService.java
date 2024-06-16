package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.Account;
import ru.chernyshev.restful.domain.Animal;
import ru.chernyshev.restful.domain.AnimalType;
import ru.chernyshev.restful.domain.Location;
import ru.chernyshev.restful.dto.AnimalDto;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.AnimalMapper;
import ru.chernyshev.restful.repository.AccountRepository;
import ru.chernyshev.restful.repository.AnimalRepository;
import ru.chernyshev.restful.repository.AnimalTypeRepository;
import ru.chernyshev.restful.repository.LocationRepository;

import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AnimalMapper animalMapper;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LocationRepository locationRepository;

    public AnimalDto createAnimal(AnimalDto dto) {

        Animal animal = new Animal();
        animal.setHeight(dto.getHeight());
        animal.setLength(dto.getLength());
        animal.setWeight(dto.getWeight());
        animal.setGender(dto.getGender());
        animal.setLifeStatus(dto.getLifeStatus());
        animal.setDeathDateTime(dto.getDeathDateTime());
        animal.setChippingDateTime(dto.getChippingDateTime());


        Long chippingLocationId = dto.getChippingLocationId();
        Location location = locationRepository.findById(chippingLocationId)
                .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + chippingLocationId));

        animal.setChippingLocation(location);


        List<AnimalType> animalTypes = dto.getAnimalTypes()
                .stream()
                .map(typeId -> animalTypeRepository.findById(typeId))
                .map(opt -> opt.orElseThrow(() -> new NotFoundException("Animal type with this id has not found: ")))
                .toList();
        animal.setAnimalTypes(animalTypes);


        Integer chipperId = dto.getChipperId();
        Account account = accountRepository.findById(chipperId)
                .orElseThrow(() -> new NotFoundException("Account with this id has not found: " + chipperId));

        animal.setChipper(account);

        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

}
