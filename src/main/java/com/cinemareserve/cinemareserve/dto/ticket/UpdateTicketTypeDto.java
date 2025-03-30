package com.cinemareserve.cinemareserve.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateTicketTypeDto {
    private String ticketNamePl;
    private String ticketNameEn;
    private Double priceMultiplier;
}