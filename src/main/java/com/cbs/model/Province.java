package com.cbs.model;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Province extends BaseEntity {
	@Column(unique=true)
    private String name;

    @OneToMany(mappedBy = "province")
    private Set<Cinema> cinemas;
}
