package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true,exclude = {"formatType","genres","prices","movieSessions","actors"})
public class Movie extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
    private String title;
    private int duration;
    private String rating_type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_release;
    private String image;
    private String thumbnail;
    private String director;
    private String year;
    private String language;
    private float avg_user_rating_star;
    private String trailer_link;
    private Boolean status;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_end;
   
    @ManyToOne
    @JoinColumn(name = "format_Type_id")
    private FormatType  formatType;
    

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;
    
    @OneToMany(mappedBy = "movie")
    private Set<Price> prices;
    
    @OneToMany(mappedBy = "movie")
    private Set<MovieSession>  movieSessions;
    
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;

}
