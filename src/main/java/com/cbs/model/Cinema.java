package com.cbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true, exclude = { "province", "cinemaScreens" })
public class Cinema extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique=true,columnDefinition = "nvarchar(255)")
    private String title;
    private String address;
    private String phone;
  
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    
   
   @OneToMany(mappedBy = "cinema")
    private Set<CinemaScreen> cinemaScreens;
	
}
