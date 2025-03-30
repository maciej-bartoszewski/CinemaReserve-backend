package com.cinemareserve.cinemareserve.service;

import com.cinemareserve.cinemareserve.dto.reservation.*;
import com.cinemareserve.cinemareserve.enitity.*;
import com.cinemareserve.cinemareserve.exception.ReservationNotFoundException;
import com.cinemareserve.cinemareserve.exception.SeatUnavailableException;
import com.cinemareserve.cinemareserve.exception.UserNotFoundException;
import com.cinemareserve.cinemareserve.mapper.ReservationMapper;
import com.cinemareserve.cinemareserve.repository.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@AllArgsConstructor
@Service
public class ReservationService {
    ReservationRepository reservationRepository;
    ScreeningRepository screeningRepository;
    TicketTypeRepository ticketTypeRepository;
    UserRepository userRepository;

    public Page<ReservationDto> getReservations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("reservationId"));
        Page<Reservation> reservations = reservationRepository.findAll(pageable);
        return reservations.map(ReservationMapper::mapReservationToReservationDto);
    }

    public ReservationDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        return ReservationMapper.mapReservationToReservationDto(reservation);
    }

    public ReservationDto addReservation(CreateReservationDto createReservationDto) {
        Screening screening = screeningRepository.findById(createReservationDto.getScreeningId())
                .orElseThrow(() -> new ReservationNotFoundException("Screening not found"));
        TicketType ticketType = ticketTypeRepository.findById(createReservationDto.getTicketTypeId())
                .orElseThrow(() -> new ReservationNotFoundException("Ticket type not found"));
        User user = userRepository.findByEmailIgnoreCase(createReservationDto.getUserEmail())
                .orElseThrow(() -> new ReservationNotFoundException("User not found"));
        Hall hall = screening.getHall();

        checkSeatAvailability(hall, createReservationDto.getRow(), createReservationDto.getSeat(), screening, null);

        Reservation reservation = ReservationMapper.mapCreateReservationDtoToReservation(createReservationDto, screening, ticketType, user);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.mapReservationToReservationDto(savedReservation);
    }

    private void checkSeatAvailability(Hall hall, int row, int seat, Screening screening, Reservation reservation) {
        if (row < 1 || row > hall.getHallRows()) {
            throw new SeatUnavailableException("Row is out of hall bounds");
        }

        if (seat < 1 || seat > hall.getSeatsPerRow()) {
            throw new SeatUnavailableException("Seat is out of hall bounds");
        }

        if (reservation != null && reservation.getRow() == row && reservation.getSeat() == seat) {
            return;
        }

        boolean seatTaken = reservationRepository.existsByScreeningAndRowAndSeat(screening, row, seat);
        if (seatTaken) {
            throw new SeatUnavailableException("Seat is already taken");
        }
    }

    public ReservationDto updateReservation(Long id, UpdateReservationDto updateReservationDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        if ((updateReservationDto.getRow() != null && !updateReservationDto.getRow().equals(reservation.getRow())) ||
                (updateReservationDto.getSeat() != null && !updateReservationDto.getSeat().equals(reservation.getSeat()))) {
            checkSeatAvailability(reservation.getScreening().getHall(),
                    updateReservationDto.getRow() != null ? updateReservationDto.getRow() : reservation.getRow(),
                    updateReservationDto.getSeat() != null ? updateReservationDto.getSeat() : reservation.getSeat(),
                    reservation.getScreening(),
                    reservation);
        }

        if (updateReservationDto.getRow() != null) {
            reservation.setRow(updateReservationDto.getRow());
        }
        if (updateReservationDto.getSeat() != null) {
            reservation.setSeat(updateReservationDto.getSeat());
        }
        if (updateReservationDto.getScreeningId() != null) {
            Screening screening = screeningRepository.findById(updateReservationDto.getScreeningId())
                    .orElseThrow(() -> new ReservationNotFoundException("Screening not found"));
            reservation.setScreening(screening);
        }
        if (updateReservationDto.getTicketTypeId() != null) {
            TicketType ticketType = ticketTypeRepository.findById(updateReservationDto.getTicketTypeId())
                    .orElseThrow(() -> new ReservationNotFoundException("Ticket type not found"));
            reservation.setTicketType(ticketType);
        }
        if (updateReservationDto.getUserEmail() != null && !updateReservationDto.getUserEmail().isEmpty()) {
            User user = userRepository.findByEmailIgnoreCase(updateReservationDto.getUserEmail())
                    .orElseThrow(() -> new ReservationNotFoundException("User not found"));
            reservation.setUser(user);
        }

        Reservation updatedReservation = reservationRepository.save(reservation);
        return ReservationMapper.mapReservationToReservationDto(updatedReservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }

    public Page<ReservationDto> getReservationsByUserEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("reservationId"));
        Page<Reservation> reservations = reservationRepository.findByUserEmail(email, pageable);
        return reservations.map(ReservationMapper::mapReservationToReservationDto);
    }

    public Page<ReservationInfoDto> getReservationsInfoByUserId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("screening.screeningDate").descending().and(Sort.by("screening.screeningTime").descending()));
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        Page<Reservation> reservations = reservationRepository.findByUserUserId(id, pageable);
        return reservations.map(ReservationMapper::mapReservationToReservationInfoDto);
    }

    public Page<ReservationInfoDto> getReservationsInfoByUserIdAndTitle(Long id, String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("screening.screeningDate").descending().and(Sort.by("screening.screeningTime").descending()));
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        Page<Reservation> reservations = reservationRepository.findByUserUserIdAndScreeningMovieTitlePlContainingIgnoreCaseOrScreeningMovieTitleEnContainingIgnoreCase(id, title, title, pageable);
        return reservations.map(ReservationMapper::mapReservationToReservationInfoDto);
    }

    public void makeReservations(Long id, MakeReservationDto makeReservationDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        IntStream.range(0, makeReservationDto.getSeats().size())
                .forEach(i -> addReservation(new CreateReservationDto(
                        makeReservationDto.getSeats().get(i).getRow(),
                        makeReservationDto.getSeats().get(i).getSeat(),
                        makeReservationDto.getScreeningId(),
                        makeReservationDto.getTicketTypeIds().get(i),
                        user.getEmail()
                )));
    }
}