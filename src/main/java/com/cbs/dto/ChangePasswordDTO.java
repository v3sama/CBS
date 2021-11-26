package com.cbs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {
	private Long id;
	private String oldPassword;
	private String newPassword;
	private String cfPassword;
}
