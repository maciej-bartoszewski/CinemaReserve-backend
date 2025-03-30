package com.cinemareserve.cinemareserve.dto.screening;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
public class ScreeningDto {
    private Long screeningId;
    private LocalDate screeningDate;
    private LocalTime screeningTime;
    private Double ticketPrice;
    private Long movieId;
    private Long hallId;
}