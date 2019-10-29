package com.cbs.services;

import com.cbs.dto.SessionDateDTO;
import com.cbs.dto.SessionList2DTO;
import com.cbs.dto.SessionListDTO;
import com.cbs.model.Movie;
import com.cbs.model.MovieSession;
import com.cbs.repository.MovieSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

	public List<SessionList2DTO> findSessionByCinemaAndMovie(int province_id, int cinema_id, int movie_id)
			throws Exception {
		return movieSessionRepository.findSessionByCinemaAndMovie(province_id, cinema_id, movie_id);
	}

	public List<MovieSession> findSessionByMovieAndCinemaScreen(long mid, long cid) {
		return movieSessionRepository.findMovieSessionByMovie_IdAndCinemaScreen_Id(mid, cid);
	}

	public void addAll(List<MovieSession> movieSessions) {
		movieSessionRepository.saveAll(movieSessions);

	}

	public List<SessionList2DTO> findSessionByCinemaAndMovieAndDate(String province_id, Long cinema_id, String movie_id,
			String time) {
		return movieSessionRepository.findSessionByCinemaAndMovieAndDate(province_id, cinema_id, movie_id, time);
	}

	public MovieSession findSessionByID2(long id){
		return movieSessionRepository.findMovieSessionById(id);
	}

	public List<SessionList2DTO> findDateOfSessionByCinemaAndMovieAndDate(int province_id, int cinema_id, int movie_id){
		return movieSessionRepository.findDateOfSessionByCinemaAndMovieAndDate(province_id, cinema_id, movie_id);
	}

	public List<SessionList2DTO> findTimeOfSessionByCinemaAndMovieAndDate(int province_id, int cinema_id, int movie_id, String date){
		return movieSessionRepository.findTimeOfSessionByCinemaAndMovieAndDate(province_id, cinema_id, movie_id, date);
	}


}
