package com.cbs.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"ticket"})
public class Rating extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String content;
	private float star;
	
	@OneToOne
	@JoinColumn(name = "order_id",referencedColumnName = "id")
	private SOrder order;
}
