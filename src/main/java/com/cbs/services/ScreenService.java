package com.cbs.services;

import com.cbs.model.Screen;
import com.cbs.repository.ScreenRepository;
import com.cbs.services.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {

    private ScreenRepository screenRepository;

    @Autowired
    public ScreenService(ScreenRepository hallRepository) {
        this.screenRepository = hallRepository;
    }

    public List<Screen> getAllScreen() {
        return screenRepository.findAll();
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
