package com.cbs.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class SessionSeat  extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "filmSession_id")
	private FilmSession filmSession;
	
	@ManyToOne
	@JoinColumn(name = "seat_id")
	private Seat seat;
	
	private boolean Available;
	
}
