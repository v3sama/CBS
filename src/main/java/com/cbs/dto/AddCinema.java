package com.cbs.dto;

import java.util.Map;

import com.cbs.model.Cinema;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddCinema {
	private Cinema cinema;
	private Map<Long,Integer> screenRows;
}
