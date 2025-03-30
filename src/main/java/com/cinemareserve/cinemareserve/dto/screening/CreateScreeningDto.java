package com.cinemareserve.cinemareserve.dto.screening;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
public class CreateScreeningDto {
    @NotNull(message = "Screening date is required")
    private LocalDate screeningDate;
    @NotNull(message = "Screening time is required")
    private LocalTime screeningTime;
    @NotNull(message = "Ticket price is required")
    private Double ticketPrice;
    @NotNull(message = "Movie ID is required")
    private Long movieId;
    @NotNull(message = "Hall ID is required")
    private Long hallId;
}