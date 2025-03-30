package com.cinemareserve.cinemareserve.mapper;

import com.cinemareserve.cinemareserve.dto.ticket.CreateTicketTypeDto;
import com.cinemareserve.cinemareserve.dto.ticket.TicketTypeDto;
import com.cinemareserve.cinemareserve.enitity.TicketType;

public class TicketTypeMapper {
    public static TicketTypeDto mapTicketTypeToTicketTypeDto(TicketType ticketType) {
        return new TicketTypeDto(ticketType.getTicketTypeId(), ticketType.getTicketNamePl(), ticketType.getTicketNameEn(), ticketType.getPriceMultiplier());
    }

    public static TicketType mapCreateTicketTypeDtoToTicketType(CreateTicketTypeDto createTicketTypeDto) {
        return new TicketType(createTicketTypeDto.getTicketTypeId(), createTicketTypeDto.getTicketNamePl(), createTicketTypeDto.getTicketNameEn(), createTicketTypeDto.getPriceMultiplier());
    }
}