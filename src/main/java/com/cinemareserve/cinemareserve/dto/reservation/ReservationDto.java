package com.cinemareserve.cinemareserve.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservationDto {
    private Long reservationId;
    private String userEmail;
    private Integer row;
    private Integer seat;
    private Long screeningId;
    private Long ticketTypeId;
}