package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Row extends BaseEntity {
	private static final long serialVersionUID = 1L;
    private String Tittle;


    @OneToMany(mappedBy = "row")
    private Set<Seat> seats;
}
