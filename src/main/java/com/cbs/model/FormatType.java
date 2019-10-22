package com.cbs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"prices","movies"})
public class FormatType extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
	private String name;
	
	@OneToMany(mappedBy = "formatType")
	private Set<Price> prices;
	
	@OneToMany(mappedBy = "formatType")
	private Set<Movie> movies;
	
	
}
