package com.cbs.model;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"cinemas"})
public class Province extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
    private String name;

    @OneToMany(mappedBy = "province")
    private Set<Cinema> cinemas;
}
