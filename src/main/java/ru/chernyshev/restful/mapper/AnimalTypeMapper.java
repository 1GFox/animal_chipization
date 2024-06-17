package ru.chernyshev.restful.mapper;

import org.springframework.stereotype.Component;
import ru.chernyshev.restful.domain.AnimalType;
import ru.chernyshev.restful.dto.AnimalTypeDto;

@Component
public class AnimalTypeMapper implements Mapper<AnimalType, AnimalTypeDto> {

    @Override
    public AnimalTypeDto toDto(AnimalType animalType) {
        return new AnimalTypeDto(animalType.getId(), animalType.getType());
    }

}
