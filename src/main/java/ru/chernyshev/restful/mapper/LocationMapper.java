package ru.chernyshev.restful.mapper;

import org.springframework.stereotype.Component;
import ru.chernyshev.restful.domain.Location;
import ru.chernyshev.restful.dto.LocationDto;

@Component
public class LocationMapper implements Mapper<Location, LocationDto> {

    @Override
    public LocationDto toDto(Location location) {
        return new LocationDto(location.getId(), location.getLatitude(), location.getLongitude());
    }
}
