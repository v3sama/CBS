package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"seats"})
public class Row extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
    private String title;


    @OneToMany(mappedBy = "row")
    private Set<Seat> seats;
}
