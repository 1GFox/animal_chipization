package ru.chernyshev.restful.conrollers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.LocationDto;
import ru.chernyshev.restful.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public LocationDto setLocation(@RequestBody LocationDto locationDto) {
        return locationService.setLocation(locationDto);
    }

    @GetMapping("/{locationId}")
    public LocationDto getLocation(@PathVariable @Min(1) Long locationId) {
        return locationService.getLocation(locationId);
    }

    @PutMapping("/{locationId}")
    public LocationDto changeLocation(@PathVariable @Min(1) Long locationId, @RequestBody LocationDto newLocationInfo) {
        return locationService.changeLocation(locationId, newLocationInfo);
    }

    @DeleteMapping("/{locationId}")
    public void deleteLocation(@PathVariable @Min(1) Long locationId) {
        locationService.deleteLocation(locationId);
    }
}
