package com.cbs.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"member"})
public class CardInformation extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
	private String card_no;
	private Date card_date;
	private String bank;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User member;
	
	
}
