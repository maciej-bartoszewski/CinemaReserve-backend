package com.cinemareserve.cinemareserve.service;

import com.cinemareserve.cinemareserve.dto.ticket.CreateTicketTypeDto;
import com.cinemareserve.cinemareserve.dto.ticket.TicketTypeDto;
import com.cinemareserve.cinemareserve.dto.ticket.UpdateTicketTypeDto;
import com.cinemareserve.cinemareserve.enitity.TicketType;
import com.cinemareserve.cinemareserve.exception.TicketTypeNotFoundException;
import com.cinemareserve.cinemareserve.mapper.TicketTypeMapper;
import com.cinemareserve.cinemareserve.repository.TicketTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TicketTypeService {
    TicketTypeRepository ticketTypeRepository;

    public Page<TicketTypeDto> getTicketTypes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ticketTypeId"));
        Page<TicketType> ticketTypes = ticketTypeRepository.findAll(pageable);
        return ticketTypes.map(TicketTypeMapper::mapTicketTypeToTicketTypeDto);
    }

    public TicketTypeDto getTicketTypeById(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TicketTypeNotFoundException("Ticket type not found"));
        return TicketTypeMapper.mapTicketTypeToTicketTypeDto(ticketType);
    }

    public TicketTypeDto addTicketType(CreateTicketTypeDto createTicketTypeDto) {
        TicketType ticketType = TicketTypeMapper.mapCreateTicketTypeDtoToTicketType(createTicketTypeDto);
        TicketType savedTicketType = ticketTypeRepository.save(ticketType);
        return TicketTypeMapper.mapTicketTypeToTicketTypeDto(savedTicketType);
    }

    public TicketTypeDto updateTicketType(Long id, UpdateTicketTypeDto updateTicketTypeDto) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TicketTypeNotFoundException("Ticket type not found"));

        if (updateTicketTypeDto.getTicketNamePl() != null && !updateTicketTypeDto.getTicketNamePl().isEmpty()) {
            ticketType.setTicketNamePl(updateTicketTypeDto.getTicketNamePl());
        }
        if (updateTicketTypeDto.getTicketNameEn() != null && !updateTicketTypeDto.getTicketNameEn().isEmpty()) {
            ticketType.setTicketNameEn(updateTicketTypeDto.getTicketNameEn());
        }
        if (updateTicketTypeDto.getPriceMultiplier() != null) {
            ticketType.setPriceMultiplier(updateTicketTypeDto.getPriceMultiplier());
        }

        TicketType updatedTicketType = ticketTypeRepository.save(ticketType);
        return TicketTypeMapper.mapTicketTypeToTicketTypeDto(updatedTicketType);
    }

    public void deleteTicketType(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TicketTypeNotFoundException("Ticket type not found"));
        ticketTypeRepository.delete(ticketType);
    }

    public Page<TicketTypeDto> findTicketTypesByPlNameOrEnName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ticketTypeId"));
        Page<TicketType> ticketTypes = ticketTypeRepository.findByTicketNamePlContainingIgnoreCaseOrTicketNameEnContainingIgnoreCase(name, name, pageable);
        return ticketTypes.map(TicketTypeMapper::mapTicketTypeToTicketTypeDto);
    }
}