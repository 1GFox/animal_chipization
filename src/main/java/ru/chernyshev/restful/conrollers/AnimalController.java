package ru.chernyshev.restful.conrollers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.domain.Gender;
import ru.chernyshev.restful.domain.LifeStatus;
import ru.chernyshev.restful.dto.AnimalDto;
import ru.chernyshev.restful.dto.ChangeAnimalTypeDto;
import ru.chernyshev.restful.service.AnimalService;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalDto> createAnimal(@RequestBody @Valid AnimalDto dto) {

        AnimalDto animalDto = animalService.createAnimal(dto);
        return new ResponseEntity<>(animalDto, HttpStatus.CREATED);
    }

    @GetMapping("/{animalId}")
    public AnimalDto getAnimalInfo(@PathVariable @Min(1) Long animalId) {
        return animalService.getAnimal(animalId);
    }

    @PutMapping("/{animalId}")
    public AnimalDto updateAnimalInfo(@PathVariable @Min(1) Long animalId, @RequestBody @Valid AnimalDto animalDto) {
        return animalService.updateAnimalInfo(animalId, animalDto);
    }

    @DeleteMapping("/{animalId}")
    public void deleteAnimal(@PathVariable @Min(1) Long animalId) {
        animalService.deleteAnimal(animalId);
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalDto> addAnimalType(@PathVariable @Min(1) Long animalId,
                                                   @PathVariable @Min(1) Long typeId) {

        AnimalDto dto = animalService.addAnimalType(animalId, typeId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{animalId}/types")
    public AnimalDto changeAnimalType(@PathVariable @Min(1) Long animalId, @RequestBody @Valid ChangeAnimalTypeDto dto) {
        return animalService.changeAnimalType(animalId, dto);
    }

    @DeleteMapping("/{animalId}/types/{typeId}")
    public AnimalDto deleteAnimalTypeFromAnimal(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long typeId) {
        return animalService.deleteAnimalTypeFromAnimal(animalId, typeId);
    }

    @GetMapping("/search")
    public List<AnimalDto> searchByFilter(@RequestParam(required = false) LocalDateTime startDateTime,
                                          @RequestParam(required = false) LocalDateTime endDateTime,
                                          @RequestParam(required = false) Gender gender,
                                          @RequestParam(required = false) @Min(1) Integer chipperId,
                                          @RequestParam(required = false) @Min(1) Long chippingLocationId,
                                          @RequestParam(required = false) LifeStatus lifeStatus,
                                          @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                          @RequestParam(defaultValue = "10") @Min(1) Integer size) {

        return animalService.searchByFilter(startDateTime, endDateTime, gender, chipperId, chippingLocationId, lifeStatus, from, size);

    }
}
