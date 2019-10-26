package com.cbs.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class CinemaRoom extends BaseEntity {
	private int rows;
	
	@ManyToOne
    @JoinColumn(name = "room_id")
	private Room room;
	
	@ManyToOne
    @JoinColumn(name = "cinema_id")
	private Cinema cinema;
	
}
