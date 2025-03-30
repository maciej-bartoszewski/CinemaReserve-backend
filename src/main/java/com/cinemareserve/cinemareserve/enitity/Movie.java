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
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "title_pl")
    private String titlePl;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "description_pl", length = 500)
    private String descriptionPl;

    @Column(name = "description_en", length = 500)
    private String descriptionEn;

    private Integer duration;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "trailer_path")
    private String trailerPath;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Screening> screenings;
}
