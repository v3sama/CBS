package com.cbs.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
}
