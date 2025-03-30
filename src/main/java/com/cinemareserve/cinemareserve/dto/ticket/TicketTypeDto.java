package com.cinemareserve.cinemareserve.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TicketTypeDto {
    private Long ticketTypeId;
    private String ticketNamePl;
    private String ticketNameEn;
    private Double priceMultiplier;
}