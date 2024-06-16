package ru.chernyshev.restful.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Float weight;
    private Float length;
    private Float height;
    private Gender gender;
    private LifeStatus lifeStatus;
    private LocalDateTime chippingDateTime;
    private Integer chipperId;
    private List<Long> animalTypes = new ArrayList<>();
    private LocalDateTime deathDateTime;
    private Long chippingLocationId;
    private List<Long> visitedLocations;

}
