package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"cinemaScreens"})
public class Screen extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(unique=true)
	@NotNull
    private String title;

    private String description;


    @OneToMany(mappedBy = "screen")
    private Set<CinemaScreen> cinemaScreens;

   
}
