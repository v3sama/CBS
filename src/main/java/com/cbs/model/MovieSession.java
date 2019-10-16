package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class MovieSession extends BaseEntity {

    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Movie film;

    @ManyToOne
    @JoinColumn(name = "cinemaRoom_Id")
    private CinemaScreen cinemaScreen;

    @OneToMany(mappedBy = "movieSession")
    private Set<Ticket> tickets;
    
    
    
}
