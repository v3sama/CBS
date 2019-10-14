package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Ticket extends BaseEntity {
	private Float price;

	 @ManyToOne
	    @JoinColumn(name = "seat_id")
	    private Seat Seat;

    @ManyToOne
    @JoinColumn(name = "scheduleSession_id")
    private ScheduleSession scheduleSession;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Order order;
}
