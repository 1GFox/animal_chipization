package ru.chernyshev.restful.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.chernyshev.restful.domain.Gender;
import ru.chernyshev.restful.domain.LifeStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimalDto {

    private Long id;


    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Float weight;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Float length;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Float height;

    @NotNull
    private Gender gender;

    private LifeStatus lifeStatus;

    private LocalDateTime chippingDateTime;

    @NotNull
    @Min(1)
    private Integer chipperId;

    private LocalDateTime deathDateTime;

    @NotNull
    @Min(1)
    private Long chippingLocationId;

    @NotNull
    private List<@Min(1) Long> animalTypes = new ArrayList<>();


    private List<Long> visitedLocations = new ArrayList<>();

}
