package com.cinemareserve.cinemareserve.service;

import com.cinemareserve.cinemareserve.dto.reservation.SeatDto;
import com.cinemareserve.cinemareserve.dto.screening.CreateScreeningDto;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningDto;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningReservationInfoDto;
import com.cinemareserve.cinemareserve.dto.screening.UpdateScreeningDto;
import com.cinemareserve.cinemareserve.dto.ticket.TicketTypeDto;
import com.cinemareserve.cinemareserve.enitity.Hall;
import com.cinemareserve.cinemareserve.enitity.Movie;
import com.cinemareserve.cinemareserve.enitity.Screening;
import com.cinemareserve.cinemareserve.exception.ScreeningNotFoundException;
import com.cinemareserve.cinemareserve.mapper.ReservationMapper;
import com.cinemareserve.cinemareserve.mapper.ScreeningMapper;
import com.cinemareserve.cinemareserve.mapper.TicketTypeMapper;
import com.cinemareserve.cinemareserve.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ScreeningService {
    ScreeningRepository screeningRepository;
    MovieRepository movieRepository;
    HallRepository hallRepository;
    TicketTypeRepository ticketTypeRepository;
    ReservationRepository reservationRepository;

    public Page<ScreeningDto> getScreenings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("screeningDate").ascending().and(Sort.by("screeningTime").ascending()));
        Page<Screening> screenings = screeningRepository.findAll(pageable);
        return screenings.map(ScreeningMapper::mapScreeningToScreeningDto);
    }

    public ScreeningDto getScreeningById(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ScreeningNotFoundException("Screening not found"));
        return ScreeningMapper.mapScreeningToScreeningDto(screening);
    }

    public ScreeningDto addScreening(CreateScreeningDto createScreeningDto) {
        Movie movie = movieRepository.findById(createScreeningDto.getMovieId())
                .orElseThrow(() -> new ScreeningNotFoundException("Movie not found"));
        Hall hall = hallRepository.findById(createScreeningDto.getHallId())
                .orElseThrow(() -> new ScreeningNotFoundException("Hall not found"));
        Screening screening = ScreeningMapper.mapCreateScreeningDtoToScreening(createScreeningDto, movie, hall);
        Screening savedScreening = screeningRepository.save(screening);
        return ScreeningMapper.mapScreeningToScreeningDto(savedScreening);
    }

    public ScreeningDto updateScreening(Long id, UpdateScreeningDto updateScreeningDto) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ScreeningNotFoundException("Screening not found"));

        if (updateScreeningDto.getScreeningDate() != null) {
            screening.setScreeningDate(updateScreeningDto.getScreeningDate());
        }
        if (updateScreeningDto.getScreeningTime() != null) {
            screening.setScreeningTime(updateScreeningDto.getScreeningTime());
        }
        if (updateScreeningDto.getTicketPrice() != null) {
            screening.setTicketPrice(updateScreeningDto.getTicketPrice());
        }
        if (updateScreeningDto.getMovieId() != null) {
            Movie movie = movieRepository.findById(updateScreeningDto.getMovieId())
                    .orElseThrow(() -> new ScreeningNotFoundException("Movie not found"));
            screening.setMovie(movie);
        }
        if (updateScreeningDto.getHallId() != null) {
            Hall hall = hallRepository.findById(updateScreeningDto.getHallId())
                    .orElseThrow(() -> new ScreeningNotFoundException("Hall not found"));
            screening.setHall(hall);
        }

        Screening updatedScreening = screeningRepository.save(screening);
        return ScreeningMapper.mapScreeningToScreeningDto(updatedScreening);
    }

    public void deleteScreening(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ScreeningNotFoundException("Screening not found"));
        screeningRepository.delete(screening);
    }

    public Page<ScreeningDto> getScreeningsByMovieTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("screeningId"));
        Page<Screening> screenings = screeningRepository.findByMovie_TitleEnContainingIgnoreCaseOrMovie_TitlePlContainingIgnoreCase(title, title, pageable);
        return screenings.map(ScreeningMapper::mapScreeningToScreeningDto);
    }

    public ScreeningReservationInfoDto reserveScreening(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ScreeningNotFoundException("Screening not found"));
        List<TicketTypeDto> ticketTypeDtos = ticketTypeRepository.findAll().stream().map(TicketTypeMapper::mapTicketTypeToTicketTypeDto).toList();
        List<SeatDto> reservedSeatDtos = reservationRepository.findAllByScreening(screening).stream().map(ReservationMapper::mapReservationToReservedSeatDto).toList();
        return ScreeningMapper.mapScreeningToScreeningReservationInfoDto(screening, reservedSeatDtos, ticketTypeDtos);
    }
}