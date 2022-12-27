package com.base.rest.dtos;

import javax.validation.constraints.NotNull;

import com.base.rest.constant.Constantes;

public class AutenticacionDTO {
	
	@NotNull(message = Constantes.VALIDATION_USERNAME_OBLIGATORIO)
	private String username;
	
	@NotNull(message = Constantes.VALIDATION_PASSWORD_OBLIGATORIO)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
