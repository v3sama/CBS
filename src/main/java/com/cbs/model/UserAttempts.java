package com.cbs.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class UserAttempts extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String username;
	private int attempts;
	private LocalDateTime lastModified;
}
