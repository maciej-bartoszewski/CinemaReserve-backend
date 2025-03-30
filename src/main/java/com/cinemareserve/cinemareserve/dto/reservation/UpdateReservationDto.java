package com.cinemareserve.cinemareserve.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateReservationDto {
    private Integer row;
    private Integer seat;
    private Long screeningId;
    private Long ticketTypeId;
    private String userEmail;
}