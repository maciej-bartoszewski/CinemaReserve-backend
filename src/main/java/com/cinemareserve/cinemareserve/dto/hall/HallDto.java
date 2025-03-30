package com.cinemareserve.cinemareserve.dto.hall;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class HallDto {
    private Long hallId;
    private String hallNamePl;
    private String hallNameEn;
    private Integer hallRows;
    private Integer seatsPerRow;
}
