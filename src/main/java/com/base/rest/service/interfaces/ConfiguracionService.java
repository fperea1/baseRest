package com.base.rest.service.interfaces;

import com.base.rest.dtos.ConfiguracionDTO;
import com.base.rest.dtos.ResultTableDTO;

public interface ConfiguracionService {

	ConfiguracionDTO getByNombre(String nombre);

	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	public void save(ConfiguracionDTO configuracion);
	
	public void update(ConfiguracionDTO configuracion);
	
	public ConfiguracionDTO getById(Integer id);
	
	public void deleteById(Integer id);
}
