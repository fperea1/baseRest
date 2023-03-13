package com.base.rest.service.interfaces;

public interface BaseService<T, I> {

	void crear(T dto);
	
	void actualizar(I id, T dto);

	T getById(I id);

	void borrar(I id);

}
