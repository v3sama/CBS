package com.cbs.dto;

import java.util.ArrayList;
import java.util.List;

import com.cbs.model.MovieSession;

public class SesionCreationDTO {
	private List<MovieSession> movieSessions = new ArrayList<>();

	// default and parameterized constructor

	public void add(MovieSession movieSession) {
		this.movieSessions.add(movieSession);
	}
	
	public List<MovieSession> getMovieSessions() {
		return movieSessions;
	}
	public void setMovieSessions(List<MovieSession> movieSessions) {
		this.movieSessions = movieSessions;
	}

}
