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
public class ScheduleSession extends BaseEntity {

    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "cinemaRoom_Id")
    private CinemaScreen cinemaScreen;

    @OneToMany(mappedBy = "ticketSession")
    private Set<Ticket> tickets;
    
    @OneToMany(mappedBy = "ticketSession")
    private Set<Ticket> ticket;
    
}
