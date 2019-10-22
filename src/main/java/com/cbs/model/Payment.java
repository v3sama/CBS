package com.cbs.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"order","cardInformation"})
public class Payment extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private int payment_status;
	private String payment_mode;
	private LocalDateTime payment_time;
	
	@OneToOne(mappedBy = "payment")
	private SOrder order;
	
	@ManyToOne
	@JoinColumn(name = "cardInfo_id")
	private CardInformation cardInformation;
	
	
}
