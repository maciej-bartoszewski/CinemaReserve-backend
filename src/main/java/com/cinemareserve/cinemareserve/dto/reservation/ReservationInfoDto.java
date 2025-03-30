package com.cinemareserve.cinemareserve.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfoDto {
    private Long movieId;
    private String movieTitlePl;
    private String movieTitleEn;
    private String posterPath;
    private LocalDate date;
    private LocalTime time;
    private String hallNamePl;
    private String hallNameEn;
    private Integer row;
    private Integer seat;
    private String ticketTypeNamePl;
    private String ticketTypeNameEn;
    private Double price;
}
