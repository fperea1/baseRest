package com.base.rest.service.interfaces;

import com.base.rest.dtos.ConfiguracionDTO;
import com.base.rest.dtos.ResultTableDTO;

public interface ConfiguracionService extends BaseService<ConfiguracionDTO, Integer> {

	ConfiguracionDTO getByNombre(String nombre);

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
}
