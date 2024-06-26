package ru.chernyshev.restful.conrollers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.VisitedLocationDto;
import ru.chernyshev.restful.service.VisitedLocationService;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping("/animals/{animalId}/locations")
public class VisitedLocationController {

    @Autowired
    private VisitedLocationService visitedLocationService;


    @PostMapping("/{pointId}")
    public ResponseEntity<VisitedLocationDto> addVisitedLocationPoint(@PathVariable @Min(1) Long animalId,
                                                                      @PathVariable @Min(1) Long pointId) {

        VisitedLocationDto dto = visitedLocationService.addVisitedLocation(animalId, pointId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @GetMapping
    public List<VisitedLocationDto> getVisitedLocations(@PathVariable @Min(1) Long animalId,
                                                        @RequestParam(required = false) LocalDateTime startDateTime,
                                                        @RequestParam(required = false) LocalDateTime endDateTime,
                                                        @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                        @RequestParam(defaultValue = "10") @Min(1) Integer size) {

        return visitedLocationService.getVisitedLocations(animalId, startDateTime, endDateTime, from, size);

    }

    @PutMapping
    public VisitedLocationDto updateVisitedLocation(@PathVariable @Min(1) Long animalId,
                                                    @RequestBody @Valid VisitedLocationDto dto) {

        return visitedLocationService.updateVisitedLocation(animalId, dto);

    }

    @DeleteMapping("/{visitedPointId}")
    public void deleteVisitedLocation(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long visitedPointId) {

        visitedLocationService.deleteVisitedLocation(animalId, visitedPointId);

    }
}
