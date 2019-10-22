package com.cbs.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"ticket"})
public class Rating extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String content;
	private float star;
	
	@OneToOne
	@JoinColumn(name = "ticket_id",referencedColumnName = "id")
	private Ticket ticket;
}
