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
@ToString(exclude = {"cinema","screen"})
public class CinemaScreen extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//số hàng ghế trong room
	private int rows;
	
	@ManyToOne
    @JoinColumn(name = "screen_id")
	private Screen screen;
	
	@ManyToOne
    @JoinColumn(name = "cinema_id")
	private Cinema cinema;
	
}
