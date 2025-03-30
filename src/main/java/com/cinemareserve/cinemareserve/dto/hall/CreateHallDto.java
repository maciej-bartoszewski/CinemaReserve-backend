package com.cinemareserve.cinemareserve.dto.hall;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateHallDto {
    private Long hallId;
    @NotBlank(message = "Hall name (PL) is required")
    private String hallNamePl;
    @NotBlank(message = "Hall name (EN) is required")
    private String hallNameEn;
    @NotNull(message = "Hall rows are required")
    private Integer hallRows;
    @NotNull(message = "Seats per row are required")
    private Integer seatsPerRow;
}
