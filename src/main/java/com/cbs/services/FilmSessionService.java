package com.cbs.services;

import com.cbs.model.ScheduleSession;
import com.cbs.repository.ScheduleSessionRepository;
import com.cbs.services.FilmSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class FilmSessionService {

    private final ScheduleSessionRepository filmSessionRepository;

    @Autowired
    public FilmSessionService(ScheduleSessionRepository filmSessionRepository) {
        this.filmSessionRepository = filmSessionRepository;
    }

    public ScheduleSession addSession(ScheduleSession filmSession) {
        return filmSessionRepository.saveAndFlush(filmSession);
    }

    public void deleteSessionById(Long id) {
        if (getSessionById(id) != null) {
            filmSessionRepository.deleteById(id);
        }
    }

    public List<ScheduleSession> getAllSession() {
        return filmSessionRepository.findAll();
    }


    public ScheduleSession getSessionById(Long id) {
        return filmSessionRepository.getOne(id);
    }


}
