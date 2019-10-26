package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true, exclude = "movies")
public class Actor extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	@NotNull
	@Size(min = 2, max = 50)
	private String name;

	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "actor_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> movies;

	public Actor(String name) {
		this.name = name;
	}

	public Actor() {
	}
}
