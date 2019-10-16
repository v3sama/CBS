package com.cbs.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity{
	private int payment_status;
	private String payment_mode;
	private LocalDateTime payment_time;
	
	@OneToOne(mappedBy = "payment")
	private SOrder order;
	
	@ManyToOne
	@JoinColumn(name = "cardInfo_id")
	private CardInformation cardInformation;
	
	
}
