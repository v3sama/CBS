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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"cards","roles","tickets"})
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String firstName;

    private String lastName;
    @Column(unique=true)
    private String phone;

    private String password;
    
    private boolean active;
    @Column(unique=true)
    private String email;

    @OneToMany(mappedBy = "member")
    private Set<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;
    
    @OneToMany(mappedBy = "member")
    private Set<CardInformation> cards;

	@ManyToMany
	@JoinTable( name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),
	            inverseJoinColumns = @JoinColumn(name = "role_id"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Role> roles = new HashSet<>();


	

}
