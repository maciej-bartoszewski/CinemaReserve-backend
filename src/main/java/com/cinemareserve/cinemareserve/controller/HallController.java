package com.cinemareserve.cinemareserve.controller;

import com.cinemareserve.cinemareserve.dto.hall.CreateHallDto;
import com.cinemareserve.cinemareserve.dto.hall.HallDto;
import com.cinemareserve.cinemareserve.dto.hall.UpdateHallDto;
import com.cinemareserve.cinemareserve.service.HallService;
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
@RequestMapping("/api/halls")
public class HallController {
    HallService hallService;

    // Get all halls
    @GetMapping
    public ResponseEntity<Page<HallDto>> getHalls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<HallDto> hallsPage = hallService.getHalls(page, size);
        return ResponseEntity.ok(hallsPage);
    }

    // Find halls by pl name or en name
    @GetMapping("/search")
    public ResponseEntity<Page<HallDto>> searchGenres(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<HallDto> hallsPage = hallService.searchHalls(query, page, size);
        return ResponseEntity.ok(hallsPage);
    }

    // Get hall by id
    @GetMapping("/{id}")
    public ResponseEntity<HallDto> getHallById(@PathVariable Long id) {
        HallDto hallDto = hallService.getHallById(id);
        return ResponseEntity.ok(hallDto);
    }

    // Add hall
    @PostMapping
    public ResponseEntity<HallDto> addHall(@Valid @RequestBody CreateHallDto createHallDto) {
        HallDto addedHallDto = hallService.addHall(createHallDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedHallDto);
    }

    // Update hall
    @PatchMapping("/{id}")
    public ResponseEntity<HallDto> updateHall(@PathVariable Long id, @Valid @RequestBody UpdateHallDto updateHallDto) {
        HallDto updatedHallDto = hallService.updateHall(id, updateHallDto);
        return ResponseEntity.ok(updatedHallDto);
    }

    // Delete hall
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
        return ResponseEntity.noContent().build();
    }
}