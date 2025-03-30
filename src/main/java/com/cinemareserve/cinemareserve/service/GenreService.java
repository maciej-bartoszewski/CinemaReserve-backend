package com.cinemareserve.cinemareserve.service;

import com.cinemareserve.cinemareserve.dto.genre.CreateGenreDto;
import com.cinemareserve.cinemareserve.dto.genre.GenreDto;
import com.cinemareserve.cinemareserve.dto.genre.UpdateGenreDto;
import com.cinemareserve.cinemareserve.dto.user.UserDto;
import com.cinemareserve.cinemareserve.enitity.Genre;
import com.cinemareserve.cinemareserve.exception.GenreAlreadyExistsException;
import com.cinemareserve.cinemareserve.exception.GenreNotFoundException;
import com.cinemareserve.cinemareserve.mapper.GenreMapper;
import com.cinemareserve.cinemareserve.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GenreService {
    GenreRepository genreRepository;

    public List<GenreDto> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(GenreMapper::mapGenreToDGenreDto).toList();
    }

    public Page<GenreDto> getGenres(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("genreId"));
        Page<Genre> genres = genreRepository.findAll(pageable);
        return genres.map(GenreMapper::mapGenreToDGenreDto);
    }

    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).
                orElseThrow(() -> new GenreNotFoundException("Genre not found"));
        return GenreMapper.mapGenreToDGenreDto(genre);
    }

    public GenreDto addGenre(CreateGenreDto createGenreDto) {
        Optional<Genre> genrePl = genreRepository.findByGenreNamePlIgnoreCase(createGenreDto.getGenreNamePl());
        Optional<Genre> genreEn = genreRepository.findByGenreNameEnIgnoreCase(createGenreDto.getGenreNameEn());
        if (genrePl.isPresent() || genreEn.isPresent()) {
            throw new GenreAlreadyExistsException("Genre already exists");
        }

        Genre genre = GenreMapper.mapCreateGenreDtoToGenre(createGenreDto);
        Genre savedGenre = genreRepository.save(genre);
        return GenreMapper.mapGenreToDGenreDto(savedGenre);
    }

    public GenreDto updateGenre(Long id, UpdateGenreDto updateGenreDto) {
        Genre genre = genreRepository.findById(id).
                orElseThrow(() -> new GenreNotFoundException("Genre not found"));

        if(updateGenreDto.getGenreNamePl() != null && !updateGenreDto.getGenreNamePl().isEmpty()) {
            if(genreRepository.findByGenreNamePlIgnoreCase(updateGenreDto.getGenreNamePl()).isPresent()
                    && !genre.getGenreNamePl().equalsIgnoreCase(updateGenreDto.getGenreNamePl())) {
                throw new GenreAlreadyExistsException("Genre already exists");
            }
            genre.setGenreNamePl(updateGenreDto.getGenreNamePl());
        }
        if (updateGenreDto.getGenreNameEn() != null && !updateGenreDto.getGenreNameEn().isEmpty()) {
            if(genreRepository.findByGenreNameEnIgnoreCase(updateGenreDto.getGenreNameEn()).isPresent()
                    && !genre.getGenreNameEn().equalsIgnoreCase(updateGenreDto.getGenreNameEn())) {
                throw new GenreAlreadyExistsException("Genre already exists");
            }
            genre.setGenreNameEn(updateGenreDto.getGenreNameEn());
        }

        Genre updatedGenre = genreRepository.save(genre);
        return GenreMapper.mapGenreToDGenreDto(updatedGenre);
    }

    public void deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id).
                orElseThrow(() -> new GenreNotFoundException("Genre not found"));
        genreRepository.delete(genre);
    }

    public Page<GenreDto> searchGenres(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("genreId"));
        Page<Genre> genres = genreRepository.findByGenreNamePlContainingIgnoreCaseOrGenreNameEnContainingIgnoreCase(query, query, pageable);
        return genres.map(GenreMapper::mapGenreToDGenreDto);
    }
}
