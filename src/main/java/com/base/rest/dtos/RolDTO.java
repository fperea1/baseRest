package com.base.rest.dtos;

import com.base.rest.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class RolDTO implements BaseDTO {

	@JsonView(View.Public.class)
	private int id;
	
	@JsonView(View.Public.class)
	private String nombre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
