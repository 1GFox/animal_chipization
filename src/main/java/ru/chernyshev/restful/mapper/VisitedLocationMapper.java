package ru.chernyshev.restful.mapper;

import org.springframework.stereotype.Component;
import ru.chernyshev.restful.domain.VisitedLocation;
import ru.chernyshev.restful.dto.VisitedLocationDto;

@Component
public class VisitedLocationMapper implements Mapper<VisitedLocation, VisitedLocationDto> {

    @Override
    public VisitedLocationDto toDto(VisitedLocation visitedLocation) {
        return new VisitedLocationDto(visitedLocation.getId(), visitedLocation.getDateTime(), visitedLocation.getLocation().getId());
    }
}
