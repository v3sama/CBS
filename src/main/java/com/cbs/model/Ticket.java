package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"member","seat","movieSession","order","price","rating"})
public class Ticket extends BaseEntity {
    @Override
    public String toString() {
        return "Ticket{" +
                "amount=" + amount +
                ", member=" + member +
                ", seat=" + seat +
                ", movieSession=" + movieSession +
                ", order=" + order +
                ", price=" + price +
                '}';
    }

    private static final long serialVersionUID = 1L;

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

}
