package com.cinemareserve.cinemareserve.repository;

import com.cinemareserve.cinemareserve.enitity.Reservation;
import com.cinemareserve.cinemareserve.enitity.Screening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByScreeningAndRowAndSeat(Screening screening, int row, int seat);

    Page<Reservation> findByUserEmail(String email, Pageable pageable);

    Page<Reservation> findByUserUserId(Long id, Pageable pageable);

    List<Reservation> findAllByScreening(Screening screening);

    Page<Reservation> findByUserUserIdAndScreeningMovieTitlePlContainingIgnoreCaseOrScreeningMovieTitleEnContainingIgnoreCase(Long id, String title, String title1, Pageable pageable);
}