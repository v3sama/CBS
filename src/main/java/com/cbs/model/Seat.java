package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
class Seat extends BaseEntity {
	private boolean isVIP;
	
    @ManyToOne
    @JoinColumn(name = "row_id")
    private Row row;
}
