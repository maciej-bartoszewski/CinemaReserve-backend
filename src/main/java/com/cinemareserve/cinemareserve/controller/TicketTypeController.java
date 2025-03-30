package com.cinemareserve.cinemareserve.controller;

import com.cinemareserve.cinemareserve.dto.ticket.CreateTicketTypeDto;
import com.cinemareserve.cinemareserve.dto.ticket.TicketTypeDto;
import com.cinemareserve.cinemareserve.dto.ticket.UpdateTicketTypeDto;
import com.cinemareserve.cinemareserve.service.TicketTypeService;
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
@RequestMapping("/api/tickets")
public class TicketTypeController {
    TicketTypeService ticketTypeService;

    // Get all ticket types
    @GetMapping
    public ResponseEntity<Page<TicketTypeDto>> getTicketTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TicketTypeDto> ticketTypesPage = ticketTypeService.getTicketTypes(page, size);
        return ResponseEntity.ok(ticketTypesPage);
    }

    // Find ticket types by pl name or en name
    @GetMapping("/search")
    public ResponseEntity<Page<TicketTypeDto>> findTicketTypesByPlNameOrEnName(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TicketTypeDto> ticketTypesPage = ticketTypeService.findTicketTypesByPlNameOrEnName(page, size, query);
        return ResponseEntity.ok(ticketTypesPage);
    }

    // Get ticket type by id
    @GetMapping("/{id}")
    public ResponseEntity<TicketTypeDto> getTicketTypeById(@PathVariable Long id) {
        TicketTypeDto ticketTypeDto = ticketTypeService.getTicketTypeById(id);
        return ResponseEntity.ok(ticketTypeDto);
    }

    // Add ticket type
    @PostMapping
    public ResponseEntity<TicketTypeDto> addTicketType(@Valid @RequestBody CreateTicketTypeDto createTicketTypeDto) {
        TicketTypeDto addedTicketTypeDto = ticketTypeService.addTicketType(createTicketTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTicketTypeDto);
    }

    // Update ticket type
    @PatchMapping("/{id}")
    public ResponseEntity<TicketTypeDto> updateTicketType(@PathVariable Long id, @Valid @RequestBody UpdateTicketTypeDto updateTicketTypeDto) {
        TicketTypeDto updatedTicketTypeDto = ticketTypeService.updateTicketType(id, updateTicketTypeDto);
        return ResponseEntity.ok(updatedTicketTypeDto);
    }

    // Delete ticket type
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketType(@PathVariable Long id) {
        ticketTypeService.deleteTicketType(id);
        return ResponseEntity.noContent().build();
    }
}