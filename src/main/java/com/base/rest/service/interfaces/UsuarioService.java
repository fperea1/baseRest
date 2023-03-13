package com.base.rest.service.interfaces;

import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.dtos.UsuarioDTO;

public interface UsuarioService extends BaseService<UsuarioDTO, Integer> {

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	UsuarioDTO findByUsername(String username);
	
	void deactivate(Integer id);
	
	void activate(Integer id);

	void cambioPasswordUser(Integer id, String username, String oldPassword, String newPassword, String newPassword2);

	void cambioPasswordAdmin(Integer id, String newPassword, String newPassword2);
}
