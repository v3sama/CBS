package com.cbs.services;

import com.cbs.model.Film;
import com.cbs.repository.FilmRepository;
import com.cbs.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    private static final int PAGE_SIZE = 10;

    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Page<Film> getAllFilmsPage(Integer pageNumber) {
        PageRequest request =
                new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "title");
        return filmRepository.findAll(request);
    }

    public Film getFilmByID(Long id) {
        return filmRepository.getOne(id);
    }

    public Page<Film> searchByTittle(String filmTittle, Integer pageNumber) {
        PageRequest request =
                new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "title");
        return filmRepository.findByTitleContaining(filmTittle, request);
    }

    public List<Film> getLast() {
        return filmRepository.findAll();
    }

    public Film addFilm(Film film) {
        return filmRepository.saveAndFlush(film);
    }

    public void deleteFilmByID(Long id) {
        if (getFilmByID(id) != null) {
            filmRepository.deleteById(id);
        }
    }
}
