package com.cinemareserve.cinemareserve.dto.genre;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateGenreDto {
    private String genreNamePl;
    private String genreNameEn;
}
