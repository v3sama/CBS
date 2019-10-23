package com.cbs.services;

import com.cbs.model.Movie;
import com.cbs.repository.MovieRepository;
import com.cbs.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class MovieService {

    private static final int PAGE_SIZE = 10;

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Page<Movie> getAllMoviesPage(Integer pageNumber) {
        PageRequest request =
               PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "title");
        return movieRepository.findAll(request);
    }

    public Movie getMovieByID(Long id) {
        return movieRepository.findById(id).get();
    }

    public Page<Movie> searchByTittle(String movieTittle, Integer pageNumber) {
        PageRequest request =
                 PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "title");
        return movieRepository.findByTitleContaining(movieTittle, request);
    }

    public List<Movie> getLast() {
        return movieRepository.findAll();
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.saveAndFlush(movie);
    }

    public void deleteMovieByID(Long id) {
        if (getMovieByID(id) != null) {
            movieRepository.deleteById(id);
        }
    }

    public List<Movie> getMovieActive(){
        return movieRepository.findByStatusTrue();
    }

//    public List<Movie> getMovieDangChieu(Date date) {
//        return movieRepository.findByDate_endAfter(date);
//    }

    public List<Movie> getMovieSapChieu(LocalDate date) {
        return movieRepository.findMovieSapChieu(date.toString());
    }
    
}
