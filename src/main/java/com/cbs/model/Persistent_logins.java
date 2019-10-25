package com.cbs.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Setter;

import lombok.Getter;

@Entity
@Getter
@Setter
public class Persistent_logins {
	private String username;
	@Id
	private String series;
	private String token;
	private Timestamp last_used;
	
}
