package com.cbs.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"member","tickets","payment"})
public class SOrder extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Double total;
//	private Boolean paid;
	private LocalDateTime orderTime;
	private String status;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private User member;

	@OneToMany(mappedBy = "order")
	private Set<Ticket> tickets;
	
	 @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_id", referencedColumnName = "id")
	private Payment payment;

	@OneToOne(mappedBy = "order")
	private Rating rating;

}
