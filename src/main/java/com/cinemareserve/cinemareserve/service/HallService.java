package com.cinemareserve.cinemareserve.service;

import com.cinemareserve.cinemareserve.dto.hall.CreateHallDto;
import com.cinemareserve.cinemareserve.dto.hall.HallDto;
import com.cinemareserve.cinemareserve.dto.hall.UpdateHallDto;
import com.cinemareserve.cinemareserve.enitity.Hall;
import com.cinemareserve.cinemareserve.exception.HallNotFoundException;
import com.cinemareserve.cinemareserve.mapper.HallMapper;
import com.cinemareserve.cinemareserve.repository.HallRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HallService {
    HallRepository hallRepository;

    public Page<HallDto> getHalls(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("hallId"));
        Page<Hall> halls = hallRepository.findAll(pageable);
        return halls.map(HallMapper::mapHallToHallDto);
    }

    public HallDto getHallById(Long id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new HallNotFoundException("Hall not found"));
        return HallMapper.mapHallToHallDto(hall);
    }

    public HallDto addHall(CreateHallDto createHallDto) {
        Hall hall = HallMapper.mapCreateHallDtoToHall(createHallDto);
        Hall savedHall = hallRepository.save(hall);
        return HallMapper.mapHallToHallDto(savedHall);
    }

    public HallDto updateHall(Long id, UpdateHallDto updateHallDto) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new HallNotFoundException("Hall not found"));

        if (updateHallDto.getHallNamePl() != null && !updateHallDto.getHallNamePl().isEmpty()) {
            hall.setHallNamePl(updateHallDto.getHallNamePl());
        }
        if (updateHallDto.getHallNameEn() != null && !updateHallDto.getHallNameEn().isEmpty()) {
            hall.setHallNameEn(updateHallDto.getHallNameEn());
        }
        if (updateHallDto.getHallRows() != null) {
            hall.setHallRows(updateHallDto.getHallRows());
        }
        if (updateHallDto.getSeatsPerRow() != null) {
            hall.setSeatsPerRow(updateHallDto.getSeatsPerRow());
        }

        Hall updatedHall = hallRepository.save(hall);
        return HallMapper.mapHallToHallDto(updatedHall);
    }

    public void deleteHall(Long id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new HallNotFoundException("Hall not found"));
        hallRepository.delete(hall);
    }

    public Page<HallDto> searchHalls(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("hallId"));
        Page<Hall> halls = hallRepository.findByHallNamePlContainingIgnoreCaseOrHallNameEnContainingIgnoreCase(query, query, pageable);
        return halls.map(HallMapper::mapHallToHallDto);
    }
}