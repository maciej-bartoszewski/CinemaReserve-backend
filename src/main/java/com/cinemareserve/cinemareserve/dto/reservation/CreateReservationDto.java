package com.cinemareserve.cinemareserve.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateReservationDto {
    @NotNull(message = "Row is required")
    private Integer row;
    @NotNull(message = "Seat is required")
    private Integer seat;
    @NotNull(message = "Screening ID is required")
    private Long screeningId;
    @NotNull(message = "Ticket type ID is required")
    private Long ticketTypeId;
    @NotNull(message = "User email is required")
    private String userEmail;
}