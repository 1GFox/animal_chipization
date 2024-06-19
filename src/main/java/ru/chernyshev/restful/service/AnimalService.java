package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.*;
import ru.chernyshev.restful.dto.AnimalDto;
import ru.chernyshev.restful.dto.ChangeAnimalTypeDto;
import ru.chernyshev.restful.exception.DataConflictException;
import ru.chernyshev.restful.exception.InvalidDataException;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.Mapper;
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
    private Mapper<Animal, AnimalDto> animalMapper;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CustomAnimalRepository customAnimalRepository;

    public AnimalDto createAnimal(AnimalDto dto) {

        List<Long> listOfTypes = dto.getAnimalTypes();

        if (listOfTypes.isEmpty()) {
            throw new InvalidDataException("Size of listTypes have to be greater then 0");
        }


        Animal animal = new Animal();
        animal.setHeight(dto.getHeight());
        animal.setLength(dto.getLength());
        animal.setWeight(dto.getWeight());
        animal.setGender(dto.getGender());
        animal.setLifeStatus(LifeStatus.ALIVE);
        animal.setChippingDateTime(LocalDateTime.now());


        Long chippingLocationId = dto.getChippingLocationId();
        Location location = locationRepository.findById(chippingLocationId)
                .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + chippingLocationId));
        animal.setChippingLocation(location);


        List<Animal> animalsChippedHere = location.getAnimalsChippedHere();
        animalsChippedHere.add(animal);


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


        List<Animal> chippedAnimals = account.getChippedAnimals();
        chippedAnimals.add(animal);


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


        animal.setWeight(dto.getWeight());


        animal.setHeight(dto.getHeight());


        animal.setLength(dto.getLength());


        animal.setGender(dto.getGender());

        if (dto.getLifeStatus() != null && dto.getLifeStatus() == LifeStatus.DEAD && animal.getLifeStatus() != LifeStatus.DEAD) {
            animal.setLifeStatus(dto.getLifeStatus());
            animal.setDeathDateTime(LocalDateTime.now());
        }
        if (animal.getLifeStatus() == LifeStatus.DEAD && dto.getLifeStatus() == LifeStatus.ALIVE) {
            animal.setLifeStatus(dto.getLifeStatus());
            animal.setDeathDateTime(null);
        }

        Integer chipperId = dto.getChipperId();
        Account account = accountRepository.findById(chipperId)
                .orElseThrow(() -> new NotFoundException("Account with this id has not found: " + chipperId));
        animal.setChipper(account);


        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();
        Long chippingLocationId = dto.getChippingLocationId();

        if (!visitedLocations.isEmpty()) {
            if (chippingLocationId.equals(visitedLocations.get(0).getLocation().getId())) {
                throw new InvalidDataException("This location is first visited location of this animal");
            }
        }

        Location chippingLocation = locationRepository.findById(chippingLocationId)
                .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + chippingLocationId));
        animal.setChippingLocation(chippingLocation);


        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

    public void deleteAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + id));

        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();

        if (!visitedLocations.isEmpty()) {
            throw new InvalidDataException("Animal has left the chipping location");
        }

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

        AnimalType oldAnimalType = animalTypeRepository.findById(dto.getOldTypeId())
                .orElseThrow(() -> new NotFoundException("Animal type with this id has not found: " + dto.getOldTypeId()));

        AnimalType newAnimalType = animalTypeRepository.findById(dto.getNewTypeId())
                .orElseThrow(() -> new NotFoundException("Type with this id has not found: " + dto.getOldTypeId()));


        if (animalTypes.contains(newAnimalType)) {
            throw new DataConflictException("This animal already has animal type with this id: " + dto.getNewTypeId());
        }

        if (!animalTypes.contains(oldAnimalType)) {
            throw new NotFoundException("This animal does not have animal type with this id: " + dto.getOldTypeId());
        }

        animalTypes.add(newAnimalType);
        animalTypes.remove(oldAnimalType);

        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

    public AnimalDto deleteAnimalTypeFromAnimal(Long animalId, Long typeId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + animalId));
        List<AnimalType> animalTypes = animal.getAnimalTypes();

        AnimalType type = animalTypeRepository.findById(typeId)
                .orElseThrow(() -> new NotFoundException("Type with this id has not found: " + typeId));


        if (!animalTypes.contains(type)) {
            throw new NotFoundException("This animal does not have this type");
        }
        if (animalTypes.size() == 1 && animalTypes.contains(type)) {
            throw new InvalidDataException("This animal has only this type");
        }

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
