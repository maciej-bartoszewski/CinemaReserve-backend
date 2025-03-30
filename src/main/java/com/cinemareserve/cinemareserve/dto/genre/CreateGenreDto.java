package com.cinemareserve.cinemareserve.dto.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateGenreDto {
    @NotBlank(message = "Genre name in Polish is mandatory")
    private String genreNamePl;
    @NotBlank(message = "Genre name in English is mandatory")
    private String genreNameEn;
}
