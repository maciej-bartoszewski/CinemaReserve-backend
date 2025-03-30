package com.cinemareserve.cinemareserve.mapper;

import com.cinemareserve.cinemareserve.dto.reservation.SeatDto;
import com.cinemareserve.cinemareserve.dto.screening.CreateScreeningDto;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningDto;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningInfoDto;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningReservationInfoDto;
import com.cinemareserve.cinemareserve.dto.ticket.TicketTypeDto;
import com.cinemareserve.cinemareserve.enitity.Hall;
import com.cinemareserve.cinemareserve.enitity.Movie;
import com.cinemareserve.cinemareserve.enitity.Screening;

import java.util.List;

public class ScreeningMapper {
    public static ScreeningDto mapScreeningToScreeningDto(Screening screening) {
        return new ScreeningDto(
                screening.getScreeningId(),
                screening.getScreeningDate(),
                screening.getScreeningTime(),
                screening.getTicketPrice(),
                screening.getMovie().getMovieId(),
                screening.getHall().getHallId()
        );
    }

    public static Screening mapCreateScreeningDtoToScreening(CreateScreeningDto createScreeningDto, Movie movie, Hall hall) {
        return new Screening(
                null,
                createScreeningDto.getScreeningDate(),
                createScreeningDto.getScreeningTime(),
                createScreeningDto.getTicketPrice(),
                hall,
                movie,
                null
        );
    }

    public static ScreeningInfoDto mapScreeningToScreeningInfoDto(Screening screening) {
        return new ScreeningInfoDto(
                screening.getScreeningId(),
                screening.getScreeningDate(),
                screening.getScreeningTime(),
                screening.getTicketPrice(),
                screening.getHall().getHallNamePl(),
                screening.getHall().getHallNameEn()
        );
    }

    public static List<ScreeningInfoDto> mapScreeningsToScreeningDtos(List<Screening> screenings) {
        return screenings.stream()
                .map(ScreeningMapper::mapScreeningToScreeningInfoDto)
                .toList();
    }

    public static ScreeningReservationInfoDto mapScreeningToScreeningReservationInfoDto(Screening screening, List<SeatDto> reservedSeats, List<TicketTypeDto> ticketTypes) {

        return new ScreeningReservationInfoDto(
                screening.getScreeningId(),
                screening.getHall().getHallRows(),
                screening.getHall().getSeatsPerRow(),
                reservedSeats,
                ticketTypes,
                screening.getTicketPrice()
        );
    }
}