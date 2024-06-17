package ru.chernyshev.restful.mapper;

import org.springframework.stereotype.Component;
import ru.chernyshev.restful.domain.Animal;
import ru.chernyshev.restful.domain.AnimalType;
import ru.chernyshev.restful.dto.AnimalDto;

import java.util.List;

@Component
public class AnimalMapper implements Mapper<Animal, AnimalDto> {
    @Override
    public AnimalDto toDto(Animal animal) {
        List<Long> animalTypes = animal.getAnimalTypes()
                .stream()
                .map(AnimalType::getId)
                .toList();

        return new AnimalDto(
                animal.getId(),
                animal.getWeight(),
                animal.getLength(),
                animal.getHeight(),
                animal.getGender(),
                animal.getLifeStatus(),
                animal.getChippingDateTime(),
                animal.getChipper().getId(),
                animalTypes,
                animal.getDeathDateTime(),
                animal.getChippingLocation().getId(),
                null
        );
    }
}
