package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.Animal;
import ru.chernyshev.restful.domain.Location;
import ru.chernyshev.restful.domain.VisitedLocation;
import ru.chernyshev.restful.dto.VisitedLocationDto;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.Mapper;
import ru.chernyshev.restful.repository.AnimalRepository;
import ru.chernyshev.restful.repository.CustomVisitedLocRepository;
import ru.chernyshev.restful.repository.LocationRepository;
import ru.chernyshev.restful.repository.VisitedLocationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class VisitedLocationService {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private Mapper<VisitedLocation, VisitedLocationDto> visitedLocationMapper;
    @Autowired
    private VisitedLocationRepository visitedLocationRepository;
    @Autowired
    private CustomVisitedLocRepository customVisitedLocRepository;

    public VisitedLocationDto addVisitedLocation(Long animalId, Long locationId) {

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + animalId));

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + locationId));

        LocalDateTime dateTimeVisitLocationPoint = LocalDateTime.now();

        VisitedLocation visitedLocation = new VisitedLocation();

        visitedLocation.setAnimal(animal);
        visitedLocation.setDateTime(dateTimeVisitLocationPoint);
        visitedLocation.setLocation(location);

        visitedLocationRepository.save(visitedLocation);

        return visitedLocationMapper.toDto(visitedLocation);
    }

    public List<VisitedLocationDto> getVisitedLocations(Long animalId,
                                                        LocalDateTime startDateTime,
                                                        LocalDateTime endDateTime,
                                                        Integer from,
                                                        Integer size) {

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + animalId));

        return customVisitedLocRepository.getVisitedLocByFilter(animal, startDateTime, endDateTime, from, size)
                .stream()
                .map(visitedLocationMapper::toDto)
                .toList();
    }

    public VisitedLocationDto updateVisitedLocation(Long animalId, VisitedLocationDto dto) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + animalId));
        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();

//        VisitedLocation visitedLocation = visitedLocations.stream()
//                .filter(currentVisitedLocation -> Objects.equals(currentVisitedLocation.getId(), dto.getId()))
//                .findAny()
//                .orElseThrow(() -> new NotFoundException("Animal with this id does not have Visited location with this id"));
//        visitedLocation.setId(dto.getLocationPointId());


        VisitedLocation visitedLocation = null;
        for (VisitedLocation currentVisitedLocation : visitedLocations) {
            if (Objects.equals(currentVisitedLocation.getId(), dto.getId())) {
                currentVisitedLocation.setId(dto.getId());
                visitedLocation = currentVisitedLocation;
                visitedLocationRepository.save(visitedLocation);
            }
        }


        return visitedLocationMapper.toDto(visitedLocation);
    }
}
