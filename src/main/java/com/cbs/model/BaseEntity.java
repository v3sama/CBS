package com.cbs.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@Getter
@Setter
@MappedSuperclass
@ToString
@EqualsAndHashCode
public class BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
}
