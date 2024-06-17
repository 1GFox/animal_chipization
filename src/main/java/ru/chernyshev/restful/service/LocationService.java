package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.Location;
import ru.chernyshev.restful.dto.LocationDto;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.LocationMapper;
import ru.chernyshev.restful.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private LocationRepository locationRepository;

    public LocationDto setLocation(LocationDto dto) {

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

        if (dto.getLongitude() != null) {
            location.setLongitude(dto.getLongitude());
        }
        if (dto.getLatitude() != null) {
            location.setLatitude(dto.getLatitude());
        }


        locationRepository.save(location);

        return locationMapper.toDto(location);
    }

    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Location with this id has not found: " + id));
        locationRepository.delete(location);
    }


}
