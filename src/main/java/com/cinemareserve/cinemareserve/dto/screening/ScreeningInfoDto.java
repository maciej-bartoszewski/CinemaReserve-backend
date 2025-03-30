package com.cinemareserve.cinemareserve.dto.screening;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningInfoDto {
    private Long screeningId;
    private LocalDate screeningDate;
    private LocalTime screeningTime;
    private Double ticketPrice;
    private String hallNamePl;
    private String hallNameEn;
}
