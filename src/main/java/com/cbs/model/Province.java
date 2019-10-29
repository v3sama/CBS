<<<<<<< HEAD
package com.cbs.model;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"cinemas"})
public class Province extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true,columnDefinition = "nvarchar(255)")
	@NotNull
    private String name;

    @OneToMany(mappedBy = "province")
    private Set<Cinema> cinemas;
}
=======
package com.cbs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true, exclude = { "cinemas" })
public class Province extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	@NotNull
	@Size(min = 2, max = 50)
	private String name;

	@OneToMany(mappedBy = "province")
	private Set<Cinema> cinemas;
}
>>>>>>> refs/remotes/origin/master
