package com.cinemareserve.cinemareserve.mapper;

import com.cinemareserve.cinemareserve.dto.genre.CreateGenreDto;
import com.cinemareserve.cinemareserve.dto.genre.GenreDto;
import com.cinemareserve.cinemareserve.enitity.Genre;

import java.util.List;

public class GenreMapper {
    public static GenreDto mapGenreToDGenreDto(Genre genre) {
        return new GenreDto(genre.getGenreId(), genre.getGenreNamePl(), genre.getGenreNameEn());
    }

    public static List<GenreDto> mapGenresToDGenreDtos(List<Genre> genres) {
        return genres.stream().map(GenreMapper::mapGenreToDGenreDto).toList();
    }

    public static Genre mapCreateGenreDtoToGenre(CreateGenreDto createGenreDto) {
        return new Genre(null, createGenreDto.getGenreNamePl(), createGenreDto.getGenreNameEn(), List.of());
    }
}
