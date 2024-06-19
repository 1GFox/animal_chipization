package ru.chernyshev.restful.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAnimalTypeDto {

    @NotNull
    @Min(1)
    private Long oldTypeId;
    @NotNull
    @Min(1)
    private Long newTypeId;
}
