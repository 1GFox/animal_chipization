package ru.chernyshev.restful.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Min(1)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long visitedLocationPointId;

    @NotNull
    @Min(1)
    private Long locationPointId;

    private LocalDateTime dateTimeOfVisitLocationPoint;
}
