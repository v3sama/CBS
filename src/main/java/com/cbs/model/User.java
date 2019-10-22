package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"tickets","cards","roles"})
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String firstName;

    private String lastName;

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
	@JoinTable(joinColumns = @JoinColumn(name = "user_id"),
	            inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

}
