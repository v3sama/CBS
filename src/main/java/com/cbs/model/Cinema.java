package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)

public class Cinema extends BaseEntity {

    private String title;
    private String address;
    private String phone;
    private String email;

//    @OneToMany(mappedBy = "cinema")
//    private Set<Room> halls;
//    
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    
    @OneToMany(mappedBy = "cinema")
    private Set<CinemaRoomReferences> cinemaRoomReferences;

}
