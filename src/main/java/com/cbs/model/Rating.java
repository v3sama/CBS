package com.cbs.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Rating extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String content;
	private float star;
	
	@OneToOne
	@JoinColumn(name = "ticket_id",referencedColumnName = "id")
	private Ticket ticket;
}
