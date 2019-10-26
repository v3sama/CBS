package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "formatType", "genres", "prices", "movieSessions", "actors" })
public class Movie extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String title;
	@Min(0)
	private int duration;
	private String rating_type;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date_release;
	private String image;
	private String thumbnail;
	private String director;
	@Min(0)
	@Max(2300)
	private String year;
	private String language;
	private float avg_user_rating_star;
	private String trailer_link;
	private Boolean status;
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date_end;

	public Movie(int duaration, String title) {
		this.duration = duaration;
		this.title = title;
	}

	public Movie(Movie movie) {

		this.duration = movie.duration;
		this.title = movie.title;
	}

	@ManyToOne()
	@JoinColumn(name = "format_Type_id")
	private FormatType formatType;

	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<Genre> genres;

	@OneToMany(mappedBy = "movie")
	private Set<Price> prices;

	@OneToMany(mappedBy = "movie")
	private Set<MovieSession> movieSessions;

	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private Set<Actor> actors;

}
