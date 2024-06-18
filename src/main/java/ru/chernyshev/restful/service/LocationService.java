package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.Location;
import ru.chernyshev.restful.dto.LocationDto;
import ru.chernyshev.restful.exception.DataConflictException;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.Mapper;
import ru.chernyshev.restful.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private Mapper<Location, LocationDto> locationMapper;
    @Autowired
    private LocationRepository locationRepository;

    public LocationDto setLocation(LocationDto dto) {

        boolean check = locationRepository.existsByLatitudeAndLongitude(dto.getLatitude(), dto.getLongitude());

        if (check){
            throw new DataConflictException("Location with this coordinates already exists");
        }

        Location location = new Location();

        location.setLongitude(dto.getLongitude());
        location.setLatitude(dto.getLatitude());

        locationRepository.save(location);

        return locationMapper.toDto(location);
    }

    public LocationDto getLocation(Long id) {
        return locationRepository.findById(id)
                .map(locationMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + id));
    }

    public LocationDto updateLocation(Long id, LocationDto dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + id));

        location.setLongitude(dto.getLongitude());
        location.setLatitude(dto.getLatitude());


        locationRepository.save(location);

        return locationMapper.toDto(location);
    }

    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + id));
        locationRepository.delete(location);
    }


}
