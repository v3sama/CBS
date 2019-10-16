package com.cbs.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FormatType extends BaseEntity{
	private String name;
	
	@OneToMany(mappedBy = "formatType")
	private Set<Price> prices;
	
	@OneToMany(mappedBy = "formatType")
	private Set<Movie> movies;
	
	
}
