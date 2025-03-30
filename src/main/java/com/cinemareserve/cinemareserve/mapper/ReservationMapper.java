package com.cinemareserve.cinemareserve.mapper;

import com.cinemareserve.cinemareserve.dto.reservation.CreateReservationDto;
import com.cinemareserve.cinemareserve.dto.reservation.ReservationDto;
import com.cinemareserve.cinemareserve.dto.reservation.ReservationInfoDto;
import com.cinemareserve.cinemareserve.dto.reservation.SeatDto;
import com.cinemareserve.cinemareserve.enitity.Reservation;
import com.cinemareserve.cinemareserve.enitity.Screening;
import com.cinemareserve.cinemareserve.enitity.TicketType;
import com.cinemareserve.cinemareserve.enitity.User;

public class ReservationMapper {
    public static ReservationDto mapReservationToReservationDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getReservationId(),
                reservation.getUser().getEmail(),
                reservation.getRow(),
                reservation.getSeat(),
                reservation.getScreening().getScreeningId(),
                reservation.getTicketType().getTicketTypeId()
        );
    }

    public static Reservation mapCreateReservationDtoToReservation(CreateReservationDto createReservationDto, Screening screening, TicketType ticketType, User user) {
        return new Reservation(
                null,
                user,
                createReservationDto.getRow(),
                createReservationDto.getSeat(),
                screening,
                ticketType
        );
    }

    public static ReservationInfoDto mapReservationToReservationInfoDto(Reservation reservation) {
        return new ReservationInfoDto(
                reservation.getScreening().getMovie().getMovieId(),
                reservation.getScreening().getMovie().getTitlePl(),
                reservation.getScreening().getMovie().getTitleEn(),
                reservation.getScreening().getMovie().getPosterPath(),
                reservation.getScreening().getScreeningDate(),
                reservation.getScreening().getScreeningTime(),
                reservation.getScreening().getHall().getHallNamePl(),
                reservation.getScreening().getHall().getHallNameEn(),
                reservation.getRow(),
                reservation.getSeat(),
                reservation.getTicketType().getTicketNamePl(),
                reservation.getTicketType().getTicketNameEn(),
                reservation.getTicketType().getPriceMultiplier() * reservation.getScreening().getTicketPrice()
        );
    }

    public static SeatDto mapReservationToReservedSeatDto(Reservation reservation) {
        return new SeatDto(
                reservation.getRow(),
                reservation.getSeat()
        );
    }
}