package com.cbs.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbs.model.Cinema;
import com.cbs.model.CinemaScreen;
import com.cbs.model.Screen;
import com.cbs.repository.CinemaScreenRepository;
import com.cbs.repository.ScreenRepository;

@Service
public class CinemaScreenService {
	//private final CinemaRepository cinemaRepository;
	private final ScreenRepository screenRepository;
	private final CinemaScreenRepository cinemaScreenRepository;
	
	@Autowired
	public CinemaScreenService( ScreenRepository screenRepository ,CinemaScreenRepository cinemaScreenRepository){
		//this.cinemaRepository = cinemaRepository;
		this.screenRepository = screenRepository;
		this.cinemaScreenRepository = cinemaScreenRepository;

	}
	
	public void addScreenToCinema(Cinema cinema, Map<Long, Integer> screens) {
		//Set<CinemaScreen> list = new HashSet<CinemaScreen>();
		for (Map.Entry<Long, Integer> screen : screens.entrySet()) {
			boolean exist =  screenRepository.existsById(screen.getKey());
			Optional<Screen> as = screenRepository.findById(screen.getKey());
			
			CinemaScreen cinemaScreen = new CinemaScreen();
			cinemaScreen.setCinema(cinema);
			cinemaScreen.setScreen(as.get());
			//cinemaScreen.setScreen(xscreen.getId());
			cinemaScreen.setRows(screen.getValue());
			cinemaScreenRepository.save(cinemaScreen);
			//list.add(cinemaScreen);
		}
		
		//cinema.setCinemaScreens(list);
		//cinemaScreenRepository.saveAll(list);
	
		// TODO Auto-generated method stub

	}

	public List<CinemaScreen> findCinemaScreenByCinema_Id(long id){
		return cinemaScreenRepository.findCinemaScreenByCinema_Id(id);
	}

	public void add(CinemaScreen entity) {
		// TODO Auto-generated method stub
		cinemaScreenRepository.save(entity);
	}

	public void remove(CinemaScreen entity) {
		// TODO Auto-generated method stub
		cinemaScreenRepository.delete(entity);
	}

	public void addAll(Set<CinemaScreen> list) {
		cinemaScreenRepository.saveAll(list);
	}


}
