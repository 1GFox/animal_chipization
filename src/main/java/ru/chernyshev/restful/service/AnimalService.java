package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.*;
import ru.chernyshev.restful.dto.AnimalDto;
import ru.chernyshev.restful.dto.ChangeAnimalTypeDto;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.AnimalMapper;
import ru.chernyshev.restful.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private CustomAnimalRepository customAnimalRepository;

    public AnimalDto createAnimal(AnimalDto dto) {

        Animal animal = new Animal();
        animal.setHeight(dto.getHeight());
        animal.setLength(dto.getLength());
        animal.setWeight(dto.getWeight());
        animal.setGender(dto.getGender());
        animal.setLifeStatus(LifeStatus.ALIVE);
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

    public AnimalDto getAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + id));

        return animalMapper.toDto(animal);
    }

    public AnimalDto updateAnimalInfo(Long id, AnimalDto dto) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + id));
        if (dto.getWeight() != null){
            animal.setWeight(dto.getWeight());
        }
        if (dto.getHeight() != null){
            animal.setHeight(dto.getHeight());
        }
        if (dto.getLength() != null){
            animal.setLength(dto.getLength());
        }
        if (dto.getGender() != null){
            animal.setGender(dto.getGender());
        }
        if (dto.getLifeStatus() != null && dto.getLifeStatus() == LifeStatus.DEAD){
            animal.setLifeStatus(dto.getLifeStatus());
            animal.setDeathDateTime(LocalDateTime.now());
        }
        if (animal.getLifeStatus() == LifeStatus.DEAD && dto.getLifeStatus() == LifeStatus.ALIVE){
            animal.setLifeStatus(dto.getLifeStatus());
            animal.setDeathDateTime(null);
        }
        if (dto.getChipperId() != null){
            Integer chipperId = dto.getChipperId();
            Account account = accountRepository.findById(chipperId)
                    .orElseThrow(() -> new NotFoundException("Account with this id has not found: " + chipperId));
            animal.setChipper(account);
        }
        if (dto.getChippingLocationId() != null){
            Long chippingLocationId = dto.getChippingLocationId();
            Location chippingLocation = locationRepository.findById(chippingLocationId)
                    .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + chippingLocationId));
            animal.setChippingLocation(chippingLocation);
        }

        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

    public void deleteAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + id));
        animalRepository.delete(animal);
    }

    public AnimalDto addAnimalType(Long animalId, Long typeId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + animalId));
        List<AnimalType> animalTypes = animal.getAnimalTypes();


        AnimalType type = animalTypeRepository.findById(typeId)
                .orElseThrow(() -> new NotFoundException("Type with this id has not found: " + typeId));
        animalTypes.add(type);

        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

    public AnimalDto changeAnimalType(Long id, ChangeAnimalTypeDto dto) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + id));
        List<AnimalType> animalTypes = animal.getAnimalTypes();

        Long oldTypeId = dto.getOldTypeId();
        AnimalType oldAnimalType = animalTypeRepository.findById(oldTypeId)
                .orElseThrow(() -> new NotFoundException("Type with this id has not found: " + oldTypeId));
        animalTypes.remove(oldAnimalType);

        Long newTypeId = dto.getNewTypeId();
        AnimalType newAnimalType = animalTypeRepository.findById(newTypeId)
                .orElseThrow(() -> new NotFoundException("Type with this id has not found: " + oldTypeId));
        animalTypes.add(newAnimalType);

        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

    public AnimalDto deleteAnimalTypeFromAnimal(Long animalId, Long typeId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + animalId));
        List<AnimalType> animalTypes = animal.getAnimalTypes();

        AnimalType type = animalTypeRepository.findById(typeId)
                .orElseThrow(() -> new NotFoundException("Type with this id has not found: " + typeId));
        animalTypes.remove(type);

        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

    public List<AnimalDto> searchByFilter(LocalDateTime startDateTime,
                                          LocalDateTime endDateTime,
                                          Gender gender,
                                          Integer chipperId,
                                          Long chipperLocationId,
                                          LifeStatus lifeStatus,
                                          Integer from,
                                          Integer size) {

        Account account = null;
        Location location = null;
        if (chipperId != null) {
            Optional<Account> optionalAccount = accountRepository.findById(chipperId);
            if (optionalAccount.isEmpty()) {
                return new ArrayList<>();
            }
            account = optionalAccount.get();
        }


        if (chipperLocationId != null) {
            Optional<Location> optionalLocation = locationRepository.findById(chipperLocationId);
            if (optionalLocation.isEmpty()) {
                return new ArrayList<>();
            }
            location = optionalLocation.get();
        }

        return customAnimalRepository.findAnimalByFilter(startDateTime, endDateTime, gender, account, location, lifeStatus, from, size)
                .stream()
                .map(animalMapper::toDto).toList();
    }
}
