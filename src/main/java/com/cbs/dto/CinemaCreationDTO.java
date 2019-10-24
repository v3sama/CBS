package com.cbs.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cbs.model.Cinema;
import com.cbs.model.CinemaScreen;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CinemaCreationDTO {
	private Cinema cinema;
	private List<CinemaScreen> cinemaScreens;
	private Set<CinemaScreen> cinemaScreenSet;
}
