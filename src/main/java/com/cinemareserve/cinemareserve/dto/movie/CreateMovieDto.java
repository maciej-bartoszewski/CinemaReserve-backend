package com.cinemareserve.cinemareserve.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CreateMovieDto {
    private Long movieId;
    @NotBlank(message = "Title (PL) is required")
    private String titlePl;
    @NotBlank(message = "Title (EN) is required")
    private String titleEn;
    @NotBlank(message = "Description (PL) is required")
    private String descriptionPl;
    @NotBlank(message = "Description (EN) is required")
    private String descriptionEn;
    @NotNull(message = "Duration is required")
    private Integer duration;
    private String posterPath;
    private String trailerPath;
    @NotNull(message = "Genre IDs are required")
    private List<Long> genreIds;
}