package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"cinemaScreens"})
public class Screen extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(unique=true)
    private String title;

    private String description;


    @OneToMany(mappedBy = "screen")
    private Set<CinemaScreen> cinemaScreens;

   
}
