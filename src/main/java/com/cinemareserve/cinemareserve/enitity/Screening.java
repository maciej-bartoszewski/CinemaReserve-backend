package com.cinemareserve.cinemareserve.enitity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id")
    private Long screeningId;

    @Column(name = "screening_date")
    private LocalDate screeningDate;

    @Column(name = "screening_time")
    private LocalTime screeningTime;

    @Column(name = "ticket_price")
    private Double ticketPrice;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservation> reservations;
}
