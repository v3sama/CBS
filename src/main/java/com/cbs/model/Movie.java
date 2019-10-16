package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Movie extends BaseEntity {

    private String title;
    private float duration;
 
    private String rating_type;
    private Date date_realease;
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
   
    @OneToMany(mappedBy = "movie")
    private Set<FormatType>  formatType;
    
    @ManyToMany(mappedBy = "movies")
    private Set<Actor> actors;

    @ManyToMany(mappedBy = "movies")
    private Set<Genre> genres;
    
    @OneToMany(mappedBy = "movies")
    private Set<Price> prices;
    

}
