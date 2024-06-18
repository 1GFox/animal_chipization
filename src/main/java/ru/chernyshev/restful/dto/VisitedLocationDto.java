package ru.chernyshev.restful.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotBlank
    @NotNull
    @NotEmpty
    private LocalDateTime dateTimeOfVisitLocationPoint;

    @NotBlank
    @NotNull
    @NotEmpty
    private Long locationPointId;
}
