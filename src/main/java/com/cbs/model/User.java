package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cbs.config.ContactNumberConstraint;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true, exclude = { "cards", "roles", "tickets" })
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2, max = 30)
	@Column(columnDefinition = "nvarchar(255) default ''")
	private String firstName;
	@NotNull
	@Size(min = 2, max = 30)
	@Column(columnDefinition = "nvarchar(255) default ''")
	private String lastName;
	@Column(unique = true)
	@ContactNumberConstraint
	private String phone;

	private String password;
	private String confirmationToken;

	private boolean active;
	@Column(unique = true)
	private String email;

	@OneToMany(mappedBy = "member")
	private Set<Ticket> tickets;

	@ManyToOne
	@JoinColumn(name = "discount_id")
	private Discount discount;

	@OneToMany(mappedBy = "member")
	private Set<CardInformation> cards;

	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Role> roles = new HashSet<>();

}
