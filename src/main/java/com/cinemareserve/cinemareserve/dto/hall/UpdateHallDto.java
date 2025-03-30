package com.cinemareserve.cinemareserve.dto.hall;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateHallDto {
    private String hallNamePl;
    private String hallNameEn;
    private Integer hallRows;
    private Integer seatsPerRow;
}
