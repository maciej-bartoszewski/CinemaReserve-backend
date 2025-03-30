package com.cinemareserve.cinemareserve.service;

import com.cinemareserve.cinemareserve.dto.movie.*;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningInfoDto;
import com.cinemareserve.cinemareserve.enitity.Genre;
import com.cinemareserve.cinemareserve.enitity.Movie;
import com.cinemareserve.cinemareserve.enitity.Screening;
import com.cinemareserve.cinemareserve.exception.MovieNotFoundException;
import com.cinemareserve.cinemareserve.mapper.MovieMapper;
import com.cinemareserve.cinemareserve.mapper.ScreeningMapper;
import com.cinemareserve.cinemareserve.repository.GenreRepository;
import com.cinemareserve.cinemareserve.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MovieService {
    MovieRepository movieRepository;
    GenreRepository genreRepository;

    public Page<MovieDto> getMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("movieId"));
        Page<Movie> movies = movieRepository.findAll(pageable);
        return movies.map(MovieMapper::mapMovieToMovieDto);
    }

    public MovieDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        return MovieMapper.mapMovieToMovieDto(movie);
    }

    public MovieDto addMovie(CreateMovieDto createMovieDto) {
        List<Genre> genres = genreRepository.findAllById(createMovieDto.getGenreIds());
        Movie movie = MovieMapper.mapCreateMovieDtoToMovie(createMovieDto, genres);
        Movie savedMovie = movieRepository.save(movie);
        return MovieMapper.mapMovieToMovieDto(savedMovie);
    }

    public MovieDto updateMovie(Long id, UpdateMovieDto updateMovieDto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        if (updateMovieDto.getTitlePl() != null && !updateMovieDto.getTitlePl().isEmpty()) {
            movie.setTitlePl(updateMovieDto.getTitlePl());
        }
        if (updateMovieDto.getTitleEn() != null && !updateMovieDto.getTitleEn().isEmpty()) {
            movie.setTitleEn(updateMovieDto.getTitleEn());
        }
        if (updateMovieDto.getDescriptionPl() != null && !updateMovieDto.getDescriptionPl().isEmpty()) {
            movie.setDescriptionPl(updateMovieDto.getDescriptionPl());
        }
        if (updateMovieDto.getDescriptionEn() != null && !updateMovieDto.getDescriptionEn().isEmpty()) {
            movie.setDescriptionEn(updateMovieDto.getDescriptionEn());
        }
        if (updateMovieDto.getDuration() != null) {
            movie.setDuration(updateMovieDto.getDuration());
        }
        if (updateMovieDto.getPosterPath() != null && !updateMovieDto.getPosterPath().isEmpty()) {
            movie.setPosterPath(updateMovieDto.getPosterPath());
        }
        if (updateMovieDto.getTrailerPath() != null && !updateMovieDto.getTrailerPath().isEmpty()) {
            movie.setTrailerPath(updateMovieDto.getTrailerPath());
        }
        if (updateMovieDto.getGenreIds() != null && !updateMovieDto.getGenreIds().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(updateMovieDto.getGenreIds());
            movie.setGenres(genres);
        }

        Movie updatedMovie = movieRepository.save(movie);
        return MovieMapper.mapMovieToMovieDto(updatedMovie);
    }

    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        movieRepository.delete(movie);
    }

    public Page<MovieDto> findMoviesByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("movieId"));
        Page<Movie> movies = movieRepository.findByTitlePlContainingIgnoreCaseOrTitleEnContainingIgnoreCase(title, title, pageable);
        return movies.map(MovieMapper::mapMovieToMovieDto);
    }

    public Map<LocalDate, List<MovieInfoDto>> getMovieSchedules() {
        List<Movie> movies = movieRepository.findAll();

        Map<LocalDate, List<Screening>> screeningsByDate = movies.stream()
                .flatMap(movie -> movie.getScreenings().stream())
                .filter(screening -> screening.getScreeningDate().isAfter(LocalDate.now()) ||
                        (screening.getScreeningDate().isEqual(LocalDate.now()) && screening.getScreeningTime().isAfter(LocalTime.now())))
                .collect(Collectors.groupingBy(Screening::getScreeningDate, TreeMap::new, Collectors.toList()));

        Map<LocalDate, List<MovieInfoDto>> movieSchedules = new TreeMap<>();

        for (Map.Entry<LocalDate, List<Screening>> entry : screeningsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Screening> screeningsForDate = entry.getValue();

            Map<Movie, List<Screening>> screeningsByMovie = screeningsForDate.stream()
                    .collect(Collectors.groupingBy(Screening::getMovie));

            List<MovieInfoDto> movieInfos = new ArrayList<>();

            for (Map.Entry<Movie, List<Screening>> movieEntry : screeningsByMovie.entrySet()) {
                Movie movie = movieEntry.getKey();
                List<Screening> screenings = movieEntry.getValue();

                List<ScreeningInfoDto> screeningInfos = screenings.stream()
                        .sorted(Comparator.comparing(Screening::getScreeningTime))
                        .map(ScreeningMapper::mapScreeningToScreeningInfoDto)
                        .toList();

                movieInfos.add(MovieMapper.mapMovieToMovieInfoDto(movie, screeningInfos));
            }

            movieInfos.sort(Comparator.comparing(MovieInfoDto::getTitlePl));
            movieSchedules.put(date, movieInfos);
        }

        return movieSchedules;
    }



    public MovieInfoDto getMovieSchedulesById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        List<Screening> screenings = movie.getScreenings().stream()
                .filter(screening -> screening.getScreeningDate().isAfter(LocalDate.now()) ||
                        (screening.getScreeningDate().isEqual(LocalDate.now()) && screening.getScreeningTime().isAfter(LocalTime.now())))
                .sorted(Comparator.comparing(Screening::getScreeningDate).thenComparing(Screening::getScreeningTime))
                .toList();

        return MovieMapper.mapMovieToMovieInfoDto(movie, ScreeningMapper.mapScreeningsToScreeningDtos(screenings));
    }
}