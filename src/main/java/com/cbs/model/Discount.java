package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Discount extends BaseEntity {
	private static final long serialVersionUID = 1L;

    private String title;

    private String description;

    private int percent;

    @OneToMany(mappedBy = "discount")
    private Set<User> users;
}
