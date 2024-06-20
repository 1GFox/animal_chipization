package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.Animal;
import ru.chernyshev.restful.domain.LifeStatus;
import ru.chernyshev.restful.domain.Location;
import ru.chernyshev.restful.domain.VisitedLocation;
import ru.chernyshev.restful.dto.VisitedLocationDto;
import ru.chernyshev.restful.exception.InvalidDataException;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.Mapper;
import ru.chernyshev.restful.repository.AnimalRepository;
import ru.chernyshev.restful.repository.CustomVisitedLocRepository;
import ru.chernyshev.restful.repository.LocationRepository;
import ru.chernyshev.restful.repository.VisitedLocationRepository;

import java.time.LocalDateTime;
import java.util.*;

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

        if (animal.getLifeStatus() == LifeStatus.DEAD) {
            throw new InvalidDataException("This animal is dead");
        }


        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();

        List<VisitedLocation> sortedVisitedLocations = visitedLocations.stream()
                .sorted(Comparator.comparing(VisitedLocation::getDateTime))
                .toList();

        if (sortedVisitedLocations.isEmpty() && animal.getChippingLocation().equals(location)) {

            throw new InvalidDataException("This animal have not left this location yet");

        }


        if (!sortedVisitedLocations.isEmpty()) {

            int size = sortedVisitedLocations.size();
            VisitedLocation lustVisitedLocation = sortedVisitedLocations.get(size - 1);

            if (lustVisitedLocation.getLocation().getId().equals(location.getId())) {

                throw new InvalidDataException("This animal is already here");

            }
        }


        LocalDateTime dateTimeVisitLocationPoint = LocalDateTime.now();

        VisitedLocation visitedLocation = new VisitedLocation();

        visitedLocation.setAnimal(animal);
        visitedLocation.setDateTime(dateTimeVisitLocationPoint);
        visitedLocation.setLocation(location);


        List<VisitedLocation> visitedLocationsWithThisLoc = location.getVisitedLocationsWithThisLoc();
        visitedLocationsWithThisLoc.add(visitedLocation);

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
        VisitedLocation visitedLocation = visitedLocationRepository.findById(dto.getVisitedLocationPointId())
                .orElseThrow(() -> new NotFoundException("Visited location with this id has not found:" + dto.getVisitedLocationPointId()));
        Location location = locationRepository.findById(dto.getLocationPointId())
                .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + dto.getLocationPointId()));


        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();
        List<VisitedLocation> sortedVisitedLocations = visitedLocations.stream()
                .sorted(Comparator.comparing(VisitedLocation::getDateTime))
                .toList();


        boolean check = sortedVisitedLocations.stream()
                .noneMatch(visitLoc -> visitLoc.getId().equals(visitedLocation.getId()));
        if (check) {
            throw new NotFoundException("This animal have not been on this locations");
        }

        if (sortedVisitedLocations.size() > 2) {

            int indexOfVisitedLoc = sortedVisitedLocations.indexOf(visitedLocation);

            if (indexOfVisitedLoc == sortedVisitedLocations.size() - 1) {

                if (sortedVisitedLocations.get(indexOfVisitedLoc - 1).getLocation().equals(location)) {
                    throw new InvalidDataException("You can`t update last or next visited location on itself");

                }

            } else if (indexOfVisitedLoc == 0) {
                if (sortedVisitedLocations.get(indexOfVisitedLoc + 1).getLocation().equals(location)) {
                    throw new InvalidDataException("You can`t update last or next visited location on itself");
                }

            } else if (sortedVisitedLocations.get(indexOfVisitedLoc - 1).getLocation().equals(location) ||
                    sortedVisitedLocations.get(indexOfVisitedLoc + 1).getLocation().equals(location)) {

                throw new InvalidDataException("You can`t update last or next visited location on itself");

            }
        }

        VisitedLocation firstVisitedLocation = sortedVisitedLocations.get(0);


        if (firstVisitedLocation.getId().equals(visitedLocation.getId()) && animal.getChippingLocation().equals(location)) {
            throw new InvalidDataException("You can`t update first visited location on chipping location of this animal");
        }

        if (visitedLocation.getLocation().equals(location)) {
            throw new InvalidDataException("You can`t update location of visited location on itself");
        }


        visitedLocation.setLocation(location);

        visitedLocationRepository.save(visitedLocation);


        return visitedLocationMapper.toDto(visitedLocation);
    }


    public void deleteVisitedLocation(Long animalId, Long visitedPointId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with this id has not found: " + animalId));
        VisitedLocation visitedLocation = visitedLocationRepository.findById(visitedPointId)
                .orElseThrow(() -> new NotFoundException("Visited location with this id has not found: " + visitedPointId));

        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();
        List<VisitedLocation> sortedVisitedLocations = visitedLocations.stream()
                .sorted(Comparator.comparing(VisitedLocation::getDateTime))
                .toList();


        boolean check = sortedVisitedLocations.stream()
                .noneMatch(visitLoc -> visitLoc.getId().equals(visitedLocation.getId()));
        if (check) {
            throw new NotFoundException("Animal with this id does not have this visited locations");
        }


        if (sortedVisitedLocations.size() > 1) {

            VisitedLocation firstVisitedLocation = sortedVisitedLocations.get(0);
            VisitedLocation secondVisitedLocation = sortedVisitedLocations.get(1);

            if (visitedLocation.getId().equals(firstVisitedLocation.getId()) &&
                    secondVisitedLocation.getLocation().getId().equals(animal.getChippingLocation().getId())) {

                visitedLocationRepository.delete(secondVisitedLocation);

            }

        }

        visitedLocationRepository.delete(visitedLocation);

    }


}