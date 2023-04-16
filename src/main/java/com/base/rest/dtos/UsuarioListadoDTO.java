package com.base.rest.dtos;

import java.time.LocalDateTime;
import java.util.Set;

import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class UsuarioListadoDTO implements BaseDTO {

	private Integer id;
	
	private String nombre;
	
	private String username;
	
	private String email;
	
	private LocalDateTime fechaAlta;
	
	private LocalDateTime fechaDesactivacion;
	
	private Boolean activo;
	
	private Set<RolDTO> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDateTime getFechaDesactivacion() {
		return fechaDesactivacion;
	}

	public void setFechaDesactivacion(LocalDateTime fechaDesactivacion) {
		this.fechaDesactivacion = fechaDesactivacion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Set<RolDTO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolDTO> roles) {
		this.roles = roles;
	}

}
