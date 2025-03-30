package com.cinemareserve.cinemareserve.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeReservationDto {
    private Long screeningId;
    private List<SeatDto> seats;
    private List<Long> ticketTypeIds;
}
