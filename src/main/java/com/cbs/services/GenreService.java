package com.cbs.services;

import com.cbs.model.Genre;
import com.cbs.repository.GenreRepository;
import com.cbs.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenre() {
        return genreRepository.findAll();
    }

    public Genre getGenreByID(Long id) {
        return genreRepository.getOne(id);
    }

    public void deleteGenreById(Long id) {
        if (getGenreByID(id) != null)
            genreRepository.deleteById(id);
    }

    public void addGenre(Genre genre) {
        genreRepository.saveAndFlush(genre);
    }
}
