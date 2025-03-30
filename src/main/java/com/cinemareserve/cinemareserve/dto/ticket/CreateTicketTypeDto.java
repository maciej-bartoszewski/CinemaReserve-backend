package com.cinemareserve.cinemareserve.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateTicketTypeDto {
    private Long ticketTypeId;
    @NotBlank(message = "Ticket name (PL) is required")
    private String ticketNamePl;
    @NotBlank(message = "Ticket name (EN) is required")
    private String ticketNameEn;
    @NotNull(message = "Price multiplier is required")
    private Double priceMultiplier;
}