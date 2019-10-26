package com.cbs.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"formatType","movie"})

public class Price extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@NotNull
	private String title;
	private String description;
	@Min(0)
	private float price;
	
	private Boolean isVIP;
	private Boolean isHoliday;
	
	
	@ManyToOne
	@JoinColumn(name="formatType_id")
	private FormatType formatType;
	
	@ManyToOne
//	@JoinColumn(name = "movie_id",insertable = false, updatable = false)
	private Movie movie;
	
//	private Long movie_id;
	
//	@ManyToOne
//	@JoinColumn(name = "ticket_id")
//	private Ticket ticket;
	
	
}
