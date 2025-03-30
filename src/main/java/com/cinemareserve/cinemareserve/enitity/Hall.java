package com.cinemareserve.cinemareserve.enitity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private Long hallId;

    @Column(name = "hall_name_pl")
    private String hallNamePl;

    @Column(name = "hall_name_en")
    private String hallNameEn;

    @Column(name = "hall_rows")
    private Integer hallRows;

    @Column(name = "seats_per_row")
    private Integer seatsPerRow;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Screening> screenings;
}
