package com.cinemareserve.cinemareserve.repository;

import com.cinemareserve.cinemareserve.enitity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByGenreNamePlIgnoreCase(String genreNamePl);
    Optional<Genre> findByGenreNameEnIgnoreCase(String genreNameEn);

    Page<Genre> findByGenreNamePlContainingIgnoreCaseOrGenreNameEnContainingIgnoreCase(String query, String query1, Pageable pageable);
}
