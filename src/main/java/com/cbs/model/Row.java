package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Row extends BaseEntity {
    private String Tittle;


    @OneToMany(mappedBy = "row")
    private Seat seat;
}
