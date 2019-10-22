package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"formatType","genres","prices","movieSessions","actors"})
public class Movie extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
    private String title;
    private float duration;
    private String rating_type;
<<<<<<< HEAD
=======
    @Column(name="date_release")
>>>>>>> 6fcaa57e7af655f77c499bd74d2851b93a648c49
    private Date date_release;
    private String image;
    private String thumbnail;
    private String director;
    private String year;
    private String language;
    private float avg_user_rating_star;
    private String trailer_link;
    private int status;
    private String description;
    private Date date_end;
   
    @ManyToOne
    @JoinColumn(name = "format_Type_id")
    private FormatType  formatType;
    

    @ManyToMany(mappedBy = "movies")
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
