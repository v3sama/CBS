package com.cbs.services;

import com.cbs.model.MovieSession;
import com.cbs.repository.MovieSessionRepository;
import com.cbs.services.MovieSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MovieSessionService {

    private final MovieSessionRepository movieSessionRepository;

    @Autowired
    public MovieSessionService(MovieSessionRepository movieSessionRepository) {
        this.movieSessionRepository = movieSessionRepository;
    }

    public MovieSession addSession(MovieSession movieSession) {
        return movieSessionRepository.saveAndFlush(movieSession);
    }

    public void deleteSessionById(Long id) {
        if (getSessionById(id) != null) {
            movieSessionRepository.deleteById(id);
        }
    }

    public List<MovieSession> getAllSession() {
        return movieSessionRepository.findAll();
    }


    public MovieSession getSessionById(Long id) {
        return movieSessionRepository.getOne(id);
    }


}
