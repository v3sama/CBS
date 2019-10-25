package com.cbs.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class CardInformation extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
	private String card_no;
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate card_date;
	@Column(nullable = false)
	private String bank;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User member;
	
	
}
