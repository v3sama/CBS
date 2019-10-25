package com.cbs.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"screen","cinema"})
public class CinemaScreen extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	//số hàng ghế trong room
	private int rows;
	
	@ManyToOne
    @JoinColumn(name = "screen_id")
	private Screen screen;
	
	@ManyToOne
    @JoinColumn(name = "cinema_id")
	private Cinema cinema;
	
	
	
}
