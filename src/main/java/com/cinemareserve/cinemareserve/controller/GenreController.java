package com.cinemareserve.cinemareserve.controller;

import com.cinemareserve.cinemareserve.dto.genre.CreateGenreDto;
import com.cinemareserve.cinemareserve.dto.genre.GenreDto;
import com.cinemareserve.cinemareserve.dto.genre.UpdateGenreDto;
import com.cinemareserve.cinemareserve.dto.user.UserDto;
import com.cinemareserve.cinemareserve.service.GenreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/genres")
public class GenreController {
    GenreService genreService;

    // Get all genres
    @GetMapping
    public ResponseEntity<Page<GenreDto>> getGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<GenreDto> genresPage = genreService.getGenres(page, size);
        return ResponseEntity.ok(genresPage);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<GenreDto> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    // Find genres by pl name or en name
    @GetMapping("/search")
    public ResponseEntity<Page<GenreDto>> searchGenres(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<GenreDto> genresPage = genreService.searchGenres(query, page, size);
        return ResponseEntity.ok(genresPage);
    }

    // Get genre by id
    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id) {
        GenreDto genreDto = genreService.getGenreById(id);
        return ResponseEntity.ok(genreDto);
    }

    // Add genre
    @PostMapping
    public ResponseEntity<GenreDto> addGenre(@Valid @RequestBody CreateGenreDto createGenreDto) {
        GenreDto addedGenreDto = genreService.addGenre(createGenreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGenreDto);
    }

    // Update genre
    @PatchMapping("/{id}")
    public ResponseEntity<GenreDto> updateGenre(@PathVariable Long id, @Valid @RequestBody UpdateGenreDto updateGenreDto) {
        GenreDto updatedGenreDto = genreService.updateGenre(id, updateGenreDto);
        return ResponseEntity.ok(updatedGenreDto);
    }

    // Delete genre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
