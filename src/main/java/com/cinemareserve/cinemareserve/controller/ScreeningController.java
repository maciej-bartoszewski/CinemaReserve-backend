package com.cinemareserve.cinemareserve.controller;

import com.cinemareserve.cinemareserve.dto.screening.CreateScreeningDto;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningDto;
import com.cinemareserve.cinemareserve.dto.screening.ScreeningReservationInfoDto;
import com.cinemareserve.cinemareserve.dto.screening.UpdateScreeningDto;
import com.cinemareserve.cinemareserve.service.ScreeningService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {
    ScreeningService screeningService;

    // Get all screenings
    @GetMapping
    public ResponseEntity<Page<ScreeningDto>> getScreenings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ScreeningDto> screeningsPage = screeningService.getScreenings(page, size);
        return ResponseEntity.ok(screeningsPage);
    }

    // Find screenings by movie title
    @GetMapping("/search")
    public ResponseEntity<Page<ScreeningDto>> getScreeningsByMovieTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ScreeningDto> screeningsPage = screeningService.getScreeningsByMovieTitle(title, page, size);
        return ResponseEntity.ok(screeningsPage);
    }

    // Get info about screening reservation
    @GetMapping("/{id}/reservations")
    public ResponseEntity<ScreeningReservationInfoDto> reserveScreening(@PathVariable Long id) {
        ScreeningReservationInfoDto screeningDto = screeningService.reserveScreening(id);
        return ResponseEntity.ok(screeningDto);
    }

    // Get screening by id
    @GetMapping("/{id}")
    public ResponseEntity<ScreeningDto> getScreeningById(@PathVariable Long id) {
        ScreeningDto screeningDto = screeningService.getScreeningById(id);
        return ResponseEntity.ok(screeningDto);
    }

    // Add screening
    @PostMapping
    public ResponseEntity<ScreeningDto> addScreening(@Valid @RequestBody CreateScreeningDto createScreeningDto) {
        ScreeningDto addedScreeningDto = screeningService.addScreening(createScreeningDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedScreeningDto);
    }

    // Update screening
    @PatchMapping("/{id}")
    public ResponseEntity<ScreeningDto> updateScreening(@PathVariable Long id, @Valid @RequestBody UpdateScreeningDto updateScreeningDto) {
        ScreeningDto updatedScreeningDto = screeningService.updateScreening(id, updateScreeningDto);
        return ResponseEntity.ok(updatedScreeningDto);
    }

    // Delete screening
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreening(@PathVariable Long id) {
        screeningService.deleteScreening(id);
        return ResponseEntity.noContent().build();
    }
}