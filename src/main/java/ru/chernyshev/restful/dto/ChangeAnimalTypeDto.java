package ru.chernyshev.restful.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAnimalTypeDto {
    private Long oldTypeId;
    private Long newTypeId;
}
