package com.cbs.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"tickets"})
public class Seat extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isVIP;
	
    @ManyToOne
    @JoinColumn(name = "row_id")
    private Row row;
    
   
    
    @OneToMany(mappedBy = "seat")
    private Set<Ticket> tickets;
    
    

}
