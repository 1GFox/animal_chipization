package ru.chernyshev.restful.conrollers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.AnimalTypeDto;
import ru.chernyshev.restful.service.AnimalTypeService;

@Validated
@RestController
@RequestMapping("/animals/types")
public class AnimalTypeController {

    @Autowired
    private AnimalTypeService animalTypeService;

    @PostMapping
    public ResponseEntity<AnimalTypeDto> createAnimalType(@RequestBody @Valid AnimalTypeDto animalTypeDto) {
        AnimalTypeDto dto = animalTypeService.createAnimalType(animalTypeDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{typeId}")
    public AnimalTypeDto getAnimalType(@PathVariable @Min(1) Long typeId) {
        return animalTypeService.getAnimalType(typeId);
    }

    @PutMapping("/{typeId}")
    public AnimalTypeDto changeAnimalType(@PathVariable @Min(1) Long typeId, @RequestBody @Valid AnimalTypeDto animalTypeDto) {
        return animalTypeService.updateAnimalType(typeId, animalTypeDto);
    }

    @DeleteMapping("/{typeId}")
    public void deleteAnimalType(@PathVariable @Min(1) Long typeId) {
        animalTypeService.deleteAnimalType(typeId);
    }
}
