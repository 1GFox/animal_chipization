package ru.chernyshev.restful.conrollers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.LocationDto;
import ru.chernyshev.restful.service.LocationService;

@Validated
@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationDto> setLocation(@RequestBody @Valid LocationDto locationDto) {
        LocationDto dto = locationService.setLocation(locationDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{pointId}")
    public LocationDto getLocation(@PathVariable @Min(1) Long pointId) {
        return locationService.getLocation(pointId);
    }

    @PutMapping("/{pointId}")
    public LocationDto changeLocation(@PathVariable @Min(1) Long pointId, @RequestBody @Valid LocationDto newLocationInfo) {
        return locationService.updateLocation(pointId, newLocationInfo);
    }

    @DeleteMapping("/{pointId}")
    public void deleteLocation(@PathVariable @Min(1) Long pointId) {
        locationService.deleteLocation(pointId);
    }
}
