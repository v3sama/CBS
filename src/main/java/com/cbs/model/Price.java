package com.cbs.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"formatType","movie"})
public class Price extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String description;
	private float price;
	
	private Boolean isVIP;
	private Boolean isHoliday;
	
	
	@ManyToOne
	@JoinColumn(name="formatType_id")
	private FormatType formatType;
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	

//	@ManyToOne
//	@JoinColumn(name = "ticket_id")
//	private Ticket ticket;
	
	
}
