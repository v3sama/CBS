package com.cbs.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity{
	private Double total;
	private Boolean Paid;
	private LocalDateTime orderTime;
	private String status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User member;

	@OneToMany(mappedBy = "order")
	private Set<Ticket> tickets;
}
