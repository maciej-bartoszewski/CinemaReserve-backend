package com.cinemareserve.cinemareserve.dto.screening;

import com.cinemareserve.cinemareserve.dto.reservation.SeatDto;
import com.cinemareserve.cinemareserve.dto.ticket.TicketTypeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningReservationInfoDto {
    private Long screeningId;
    private Integer hallRows;
    private Integer seatsPerRow;
    private List<SeatDto> reservedSeats;
    private List<TicketTypeDto> ticketTypes;
    private Double baseTicketPrice;
}
