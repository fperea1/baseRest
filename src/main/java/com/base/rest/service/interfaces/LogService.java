package com.base.rest.service.interfaces;

import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.entities.Log;

public interface LogService {
	
	void crear(Log log);

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
}
