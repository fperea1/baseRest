package com.base.rest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.base.rest.constant.Constantes;
import com.googlecode.jmapper.annotations.JGlobalMap;

@Entity
@Table(name = "configuracion")
@JGlobalMap
public class Configuracion extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre", length = 50, unique = true)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_CONFIG_SIZE)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	private String nombre;
	
	@Column(name = "valor", length = 500)
	@Size(min = 1, max = 500, message = Constantes.VALIDATION_VALOR_CONFIG_SIZE)
	@NotNull(message = Constantes.VALIDATION_VALOR_OBLIGATORIO)
	private String valor;

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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
}
