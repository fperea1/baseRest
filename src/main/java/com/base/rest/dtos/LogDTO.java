package com.base.rest.dtos;

import java.time.LocalDateTime;

import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class LogDTO implements BaseDTO {
	
	private Integer id;
	
	private String username;
	
	private String entidad;
	
	private String accion;
	
	private String observaciones;
	
	private LocalDateTime fecha;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	@Override
	public String getNombre() {
		// Creado por herencia
		return "";
	}
	
}
