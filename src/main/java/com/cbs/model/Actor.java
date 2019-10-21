package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"movies"})
public class Actor extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
    private String name;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movies;
}
