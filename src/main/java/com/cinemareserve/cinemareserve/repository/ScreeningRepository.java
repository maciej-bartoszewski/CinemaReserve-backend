package com.cinemareserve.cinemareserve.repository;

import com.cinemareserve.cinemareserve.enitity.Screening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    Page<Screening> findByMovie_TitleEnContainingIgnoreCaseOrMovie_TitlePlContainingIgnoreCase(String title,String title1, Pageable pageable);
}