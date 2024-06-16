package ru.chernyshev.restful.conrollers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.AnimalTypeDto;
import ru.chernyshev.restful.service.AnimalTypeService;

@RestController
@RequestMapping("/animals/types")
public class AnimalTypeController {

    @Autowired
    private AnimalTypeService animalTypeService;

    @PostMapping
    public AnimalTypeDto createAnimalType(@RequestBody AnimalTypeDto animalTypeDto) {
        return animalTypeService.createAnimalType(animalTypeDto);
    }

    @GetMapping("/{typeId}")
    public AnimalTypeDto getAnimalType(@PathVariable @Min(1) Long typeId) {
        return animalTypeService.getAnimalType(typeId);
    }

    @PutMapping("/{typeId}")
    public AnimalTypeDto changeAnimalType(@PathVariable @Min(1) Long typeId, @RequestBody AnimalTypeDto animalTypeDto) {
        return animalTypeService.setAnimalType(typeId, animalTypeDto);
    }

    @DeleteMapping("/{typeId}")
    public void deleteAnimalType(@PathVariable @Min(1) Long typeId) {
        animalTypeService.deleteAnimalType(typeId);
    }
}
