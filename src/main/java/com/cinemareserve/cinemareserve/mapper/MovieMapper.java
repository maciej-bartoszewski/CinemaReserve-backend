package com.cinemareserve.cinemareserve.mapper;

import com.cinemareserve.cinemareserve.dto.movie.CreateMovieDto;
import com.cinemareserve.cinemareserve.dto.movie.MovieDto;
import com.cinemareserve.cinemareserve.dto.movie.MovieInfoDto;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningInfoDto;
import com.cinemareserve.cinemareserve.enitity.Genre;
import com.cinemareserve.cinemareserve.enitity.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {
    public static MovieDto mapMovieToMovieDto(Movie movie) {
        List<Long> genreIds = movie.getGenres().stream()
                .map(Genre::getGenreId)
                .collect(Collectors.toList());
        return new MovieDto(movie.getMovieId(), movie.getTitlePl(), movie.getTitleEn(), movie.getDescriptionPl(), movie.getDescriptionEn(), movie.getDuration(), movie.getPosterPath(), movie.getTrailerPath(), genreIds);
    }

    public static Movie mapCreateMovieDtoToMovie(CreateMovieDto createMovieDto, List<Genre> genres) {
        return new Movie(createMovieDto.getMovieId(), createMovieDto.getTitlePl(), createMovieDto.getTitleEn(), createMovieDto.getDescriptionPl(), createMovieDto.getDescriptionEn(), createMovieDto.getDuration(), createMovieDto.getPosterPath(), createMovieDto.getTrailerPath(), genres, null);
    }

    public static MovieInfoDto mapMovieToMovieInfoDto(Movie movie, List<ScreeningInfoDto> screenings) {
        return new MovieInfoDto(movie.getMovieId(), movie.getTitlePl(), movie.getTitleEn(), movie.getDescriptionPl(), movie.getDescriptionEn(), movie.getDuration(), movie.getPosterPath(), movie.getTrailerPath(), GenreMapper.mapGenresToDGenreDtos(movie.getGenres()), screenings);
    }
}