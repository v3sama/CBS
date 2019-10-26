package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import java.util.Set;


@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class Discount extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@NotNull
    private String title;

    private String description;

    private int percent;

    @OneToMany(mappedBy = "discount")
    private Set<User> users;
}
