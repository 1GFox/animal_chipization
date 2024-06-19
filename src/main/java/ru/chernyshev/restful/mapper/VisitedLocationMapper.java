package ru.chernyshev.restful.mapper;

import org.springframework.stereotype.Component;
import ru.chernyshev.restful.domain.VisitedLocation;
import ru.chernyshev.restful.dto.VisitedLocationDto;

@Component
public class VisitedLocationMapper implements Mapper<VisitedLocation, VisitedLocationDto> {

    @Override
    public VisitedLocationDto toDto(VisitedLocation visitedLocation) {
        VisitedLocationDto visitedLocationDto = new VisitedLocationDto();

        visitedLocationDto.setId(visitedLocation.getId());
        visitedLocationDto.setDateTimeOfVisitLocationPoint(visitedLocation.getDateTime());
        visitedLocationDto.setLocationPointId(visitedLocation.getLocation().getId());

        return visitedLocationDto;
    }

}
