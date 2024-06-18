package ru.chernyshev.restful.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitedLocationDto {
    private Long id;
    private LocalDateTime dateTimeOfVisitLocationPoint;
    private Long locationPointId;
}
