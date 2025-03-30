package com.cinemareserve.cinemareserve.mapper;

import com.cinemareserve.cinemareserve.dto.hall.CreateHallDto;
import com.cinemareserve.cinemareserve.dto.hall.HallDto;
import com.cinemareserve.cinemareserve.enitity.Hall;

import java.util.List;

public class HallMapper {
    public static HallDto mapHallToHallDto(Hall hall) {
        return new HallDto(hall.getHallId(), hall.getHallNamePl(), hall.getHallNameEn(), hall.getHallRows(), hall.getSeatsPerRow());
    }

    public static Hall mapCreateHallDtoToHall(CreateHallDto createHallDto) {
        return new Hall(createHallDto.getHallId(), createHallDto.getHallNamePl(), createHallDto.getHallNameEn(), createHallDto.getHallRows(), createHallDto.getSeatsPerRow(), List.of());
    }
}