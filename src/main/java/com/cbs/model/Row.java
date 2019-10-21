package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"seats"})
public class Row extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
    private String Tittle;


    @OneToMany(mappedBy = "row")
    private Set<Seat> seats;
}
