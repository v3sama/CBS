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
	private static final long serialVersionUID = 1L;
	private String tittle;
	private String description;
	private float price;

	private boolean isVIP;
	private boolean isHoliday;
	

	@ManyToOne
	@JoinColumn(name="formatType_id")
	private FormatType formatType;
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
//	
//	@ManyToOne
//	@JoinColumn(name = "ticket_id")
//	private Ticket ticket;
	
	
}
