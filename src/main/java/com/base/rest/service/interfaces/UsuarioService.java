package com.base.rest.service.interfaces;

import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.dtos.UsuarioDTO;

public interface UsuarioService {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	void save(UsuarioDTO usuario);
	
	void update(UsuarioDTO usuario);
	
	UsuarioDTO findByUsername(String username);
	
	UsuarioDTO getById(Integer id);
	
	void deleteById(Integer id);
	
	void deactivate(Integer id);
	
	void activate(Integer id);

	void cambioPasswordUser(Integer id, String username, String oldPassword, String newPassword, String newPassword2);

	void cambioPasswordAdmin(Integer id, String newPassword, String newPassword2);
}
