package com.cinemareserve.cinemareserve.controller;

import com.cinemareserve.cinemareserve.dto.movie.*;
import com.cinemareserve.cinemareserve.service.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController {
    MovieService movieService;

    // Get all movies
    @GetMapping
    public ResponseEntity<Page<MovieDto>> getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MovieDto> moviesPage = movieService.getMovies(page, size);
        return ResponseEntity.ok(moviesPage);
    }

    // Get all movies schedules
    @GetMapping("/schedules")
    public ResponseEntity<Map<LocalDate, List<MovieInfoDto>>> getMovieSchedules() {
        Map<LocalDate, List<MovieInfoDto>> movieSchedules = movieService.getMovieSchedules();
        return ResponseEntity.ok(movieSchedules);
    }

    // Get movie schedules by id
    @GetMapping("/{id}/info")
    public ResponseEntity<MovieInfoDto> getMovieSchedulesById(@PathVariable Long id) {
        MovieInfoDto movieInfoDto = movieService.getMovieSchedulesById(id);
        return ResponseEntity.ok(movieInfoDto);
    }

    // Find movies by pl title or en title
    @GetMapping("/search")
    public ResponseEntity<Page<MovieDto>> findMoviesByTitle(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MovieDto> moviesPage = movieService.findMoviesByTitle(title, page, size);
        return ResponseEntity.ok(moviesPage);
    }

    // Get movie by id
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        MovieDto movieDto = movieService.getMovieById(id);
        return ResponseEntity.ok(movieDto);
    }

    // Add movie
    @PostMapping
    public ResponseEntity<MovieDto> addMovie(@Valid @RequestBody CreateMovieDto createMovieDto) {
        MovieDto addedMovieDto = movieService.addMovie(createMovieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMovieDto);
    }

    // Update movie
    @PatchMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @Valid @RequestBody UpdateMovieDto updateMovieDto) {
        MovieDto updatedMovieDto = movieService.updateMovie(id, updateMovieDto);
        return ResponseEntity.ok(updatedMovieDto);
    }

    // Delete movie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}