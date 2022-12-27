package com.base.rest.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.base.rest.constant.Constantes;
import com.googlecode.jmapper.annotations.JGlobalMap;

@Entity
@Table(name = "usuarios")
@JGlobalMap
public class Usuario extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre", length = 50)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_USUARIO_SIZE)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	private String nombre;
	
	@Column(name = "username", length = 50, unique = true)
	@Size(min = 5, max = 50, message = Constantes.VALIDATION_USERNAME_USUARIO_SIZE)
	@NotNull(message = Constantes.VALIDATION_USERNAME_OBLIGATORIO)
	private String username;
	
	@Column(name = "password", length = 100, updatable=false)
	@Size(min = 10, max = 100, message = Constantes.VALIDATION_PASSWORD_USUARIO_SIZE)
	@NotNull(message = Constantes.VALIDATION_PASSWORD_OBLIGATORIO)
	private String password;
	
	@Column(name = "email", length = 100)
	@NotNull(message = Constantes.VALIDATION_EMAIL_OBLIGATORIO)
	@Email(message = Constantes.VALIDATION_EMAIL_USUARIO_FORMATO)
	private String email;
	
	@Column(name = "fecha_alta")
	@NotNull(message = Constantes.VALIDATION_FECHA_ALTA_OBLIGATORIO)
	private Date fechaAlta;
	
	@Column(name = "fecha_desactivacion")
	private Date fechaDesactivacion;
	
	@Column(name = "activo")
	@NotNull(message = Constantes.VALIDATION_ACTIVO_OBLIGATORIO)
	private Boolean activo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "roles_usuario",
    			joinColumns = {@JoinColumn(name = "id_usuario", referencedColumnName = "id")},
    			inverseJoinColumns = {@JoinColumn(name = "id_rol", referencedColumnName = "id")})
	private Set<Rol> roles;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaDesactivacion() {
		return fechaDesactivacion;
	}

	public void setFechaDesactivacion(Date fechaDesactivacion) {
		this.fechaDesactivacion = fechaDesactivacion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

}
