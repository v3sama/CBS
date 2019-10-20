package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Screen extends BaseEntity {
	@Column(unique=true)
    private String title;

    private String description;


    @OneToMany(mappedBy = "screen")
    private Set<CinemaScreen> cinemaScreens;

   
}
