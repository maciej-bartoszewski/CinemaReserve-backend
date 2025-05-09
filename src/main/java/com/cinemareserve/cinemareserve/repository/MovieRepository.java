package com.cinemareserve.cinemareserve.repository;

import com.cinemareserve.cinemareserve.enitity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByTitlePlContainingIgnoreCaseOrTitleEnContainingIgnoreCase(String title, String title1, Pageable pageable);
}