package ru.chernyshev.restful.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernyshev.restful.dto.AnimalDto;
import ru.chernyshev.restful.service.AnimalService;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalDto> createAnimal(@RequestBody AnimalDto dto) {

        AnimalDto animalDto = animalService.createAnimal(dto);
        return new ResponseEntity<AnimalDto>(animalDto, HttpStatus.CREATED);
    }
}
