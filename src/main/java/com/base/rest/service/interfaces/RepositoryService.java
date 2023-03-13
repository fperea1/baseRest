package com.base.rest.service.interfaces;

public interface RepositoryService <T, I> {

	void save(T dto);
	
	void update(I id, T dto);

	T findById(I id);

	void deleteById(I id);

}
