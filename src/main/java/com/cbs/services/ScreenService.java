package com.cbs.services;

import com.cbs.model.Cinema;
import com.cbs.model.CinemaScreen;
import com.cbs.model.Screen;
import com.cbs.repository.CinemaScreenRepository;
import com.cbs.repository.ScreenRepository;
import com.cbs.services.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreenService {

    private ScreenRepository screenRepository;
    private CinemaScreenRepository cinemaScreenRepository;

    @Autowired
    public ScreenService(ScreenRepository screenRepository,CinemaScreenRepository cinemaScreenRepository) {
        this.screenRepository = screenRepository;
        this.cinemaScreenRepository = cinemaScreenRepository;
    }

    public List<Screen> getAllScreen() {
        return screenRepository.findAll();
    }
    
    public List<Screen> getScreenByCinema(Long cinemaId) {
    
        List<Screen> screens = cinemaScreenRepository.findAll().parallelStream()
        		.filter(cs -> cs.getCinema().getId() == cinemaId).map(CinemaScreen::getScreen).collect(Collectors.toList());
        		
       return screens; 		
    }

    public Screen getScreenById(Long id) {
        return screenRepository.getOne(id);
    }


    public void deleteScreenByID(Long id) {
        if (getScreenById(id) != null) {
        	screenRepository.deleteById(id);
        }
    }

    public Screen addScreen(Screen screen) {
        return screenRepository.saveAndFlush(screen);
    }

}
