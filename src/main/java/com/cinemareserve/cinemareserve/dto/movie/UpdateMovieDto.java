package com.cinemareserve.cinemareserve.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UpdateMovieDto {
    private String titlePl;
    private String titleEn;
    private String descriptionPl;
    private String descriptionEn;
    private Integer duration;
    private String posterPath;
    private String trailerPath;
    private List<Long> genreIds;
}