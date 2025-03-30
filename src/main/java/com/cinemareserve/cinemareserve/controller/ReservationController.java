package com.cinemareserve.cinemareserve.controller;

import com.cinemareserve.cinemareserve.dto.reservation.*;
import com.cinemareserve.cinemareserve.service.ReservationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    ReservationService reservationService;

    // Get all reservations
    @GetMapping
    public ResponseEntity<Page<ReservationDto>> getReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReservationDto> reservationsPage = reservationService.getReservations(page, size);
        return ResponseEntity.ok(reservationsPage);
    }

    // Find reservations by user email
    @GetMapping("/search")
    public ResponseEntity<Page<ReservationDto>> getReservationsByUserEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReservationDto> reservationsPage = reservationService.getReservationsByUserEmail(email, page, size);
        return ResponseEntity.ok(reservationsPage);
    }

    // Find all reservations info by user id
    @GetMapping("/users/{id}/info")
    public ResponseEntity<Page<ReservationInfoDto>> getReservationsInfoByUserEmail(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReservationInfoDto> reservationsInfoPage = reservationService.getReservationsInfoByUserId(id, page, size);
        return ResponseEntity.ok(reservationsInfoPage);
    }

    // Find all reservations info for user by title
    @GetMapping("/users/{id}/info/search")
    public ResponseEntity<Page<ReservationInfoDto>> getReservationsInfoByUserEmailAndTitle(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReservationInfoDto> reservationsInfoPage = reservationService.getReservationsInfoByUserIdAndTitle(id, title, page, size);
        return ResponseEntity.ok(reservationsInfoPage);
    }

    // Get reservation by id
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        ReservationDto reservationDto = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservationDto);
    }

    // Make reservations by user id
    @PostMapping("/users/{id}")
    public ResponseEntity<Null> makeReservation(@PathVariable Long id, @RequestBody MakeReservationDto makeReservationDto) {
        System.out.println("makeReservationDto: " + makeReservationDto);
        reservationService.makeReservations(id, makeReservationDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // Add reservation
    @PostMapping
    public ResponseEntity<ReservationDto> addReservation(@Valid @RequestBody CreateReservationDto createReservationDto) {
        ReservationDto addedReservationDto = reservationService.addReservation(createReservationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedReservationDto);
    }

    // Update reservation
    @PatchMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @Valid @RequestBody UpdateReservationDto updateReservationDto) {
        ReservationDto updatedReservationDto = reservationService.updateReservation(id, updateReservationDto);
        return ResponseEntity.ok(updatedReservationDto);
    }

    // Delete reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}