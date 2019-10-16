package com.cbs.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Price extends BaseEntity {
	private String tittle;
	private String Description;
	private float Price;

	private boolean isVIP;
	private boolean isHoliday;
	

	@ManyToOne
	@JoinColumn(name="formatType_id")
	private FormatType FormatType;
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
//	
//	@ManyToOne
//	@JoinColumn(name = "ticket_id")
//	private Ticket ticket;
	
	
}
