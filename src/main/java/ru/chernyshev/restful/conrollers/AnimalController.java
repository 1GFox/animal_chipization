package ru.chernyshev.restful.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.AnimalTypeDto;
import ru.chernyshev.restful.service.AnimalTypeService;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalTypeService animalTypeService;

    @PostMapping("/types")
    public AnimalTypeDto createAnimalType(@RequestBody AnimalTypeDto animalTypeDto) {
        return animalTypeService.createAnimalType(animalTypeDto);
    }

    @GetMapping("/types/{typeId}")
    public AnimalTypeDto getAnimalType(@PathVariable Long typeId) {
        return animalTypeService.getAnimalType(typeId);
    }

    @PutMapping("/types/{typeId}")
    public AnimalTypeDto changeAnimalType(@PathVariable Long typeId, @RequestBody AnimalTypeDto animalTypeDto) {
        return animalTypeService.setAnimalType(typeId, animalTypeDto);
    }

    @DeleteMapping("/types/{typeId}")
    public void deleteAnimalType(@PathVariable Long typeId) {
        animalTypeService.deleteAnimalType(typeId);
    }
}
