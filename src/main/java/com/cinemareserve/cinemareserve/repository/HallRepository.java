package com.cinemareserve.cinemareserve.repository;

import com.cinemareserve.cinemareserve.enitity.Hall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    Page<Hall> findByHallNamePlContainingIgnoreCaseOrHallNameEnContainingIgnoreCase(String query, String query2, Pageable pageable);
}