package com.cinemareserve.cinemareserve.repository;

import com.cinemareserve.cinemareserve.enitity.TicketType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
    Page<TicketType> findByTicketNamePlContainingIgnoreCaseOrTicketNameEnContainingIgnoreCase(String name, String name1, Pageable pageable);
}