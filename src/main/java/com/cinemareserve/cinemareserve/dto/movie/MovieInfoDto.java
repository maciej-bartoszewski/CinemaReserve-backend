package com.cinemareserve.cinemareserve.dto.movie;

import com.cinemareserve.cinemareserve.dto.genre.GenreDto;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfoDto {
    private Long movieId;
    private String titlePl;
    private String titleEn;
    private String descriptionPl;
    private String descriptionEn;
    private Integer duration;
    private String posterPath;
    private String trailerPath;
    private List<GenreDto> genres;
    private List<ScreeningInfoDto> screenings;
}