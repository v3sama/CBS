package com.cbs.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"users"})
public class Role extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
	private String name;
	
	@ManyToMany
	@JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "role_id"),
	            inverseJoinColumns = @JoinColumn(name = "user_id"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<User> users = new HashSet<>();
}
