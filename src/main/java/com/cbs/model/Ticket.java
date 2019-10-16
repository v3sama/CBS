package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Ticket extends BaseEntity {
	private Float amount;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private User member;
	
	@ManyToOne
	@JoinColumn(name = "seat_id")
	private Seat seat;

	@ManyToOne
    @JoinColumn(name = "movieSession_id")
    private MovieSession movieSession;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private SOrder order;
    
    @ManyToOne
    @JoinColumn(name = "price_id")
    private Price price;	
    
    @OneToOne(mappedBy = "ticket")
    private Rating rating;	
}
