package com.base.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.dtos.LogDTO;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Log;
import com.base.rest.repositories.LogRepository;
import com.base.rest.service.interfaces.LogService;
import com.base.rest.utils.Converter;
import com.base.rest.utils.bd.FiltroTablasView;
import com.base.rest.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class LogServiceImpl extends SpecificationBaseServiceImpl implements LogService {
	
	@Autowired
	private LogRepository repository;
	
	private Converter<Log, LogDTO> converterDTO;

	public LogServiceImpl() {
		super();
		converterDTO = new Converter<>(Log.class, LogDTO.class);
	}

	@Transactional
	@Override
	public void crear(Log log) {
		
		repository.save(log);
	}

	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return converterDTO.convertToResultTableDTO(repository.findAll(spec, pageable));
	}

}
