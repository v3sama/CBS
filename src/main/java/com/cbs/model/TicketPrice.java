package com.cbs.model;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class TicketPrice extends BaseEntity {
	private String tittle;
	private String Description;
	private float Price;
}
