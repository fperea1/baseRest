package com.base.rest.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.dtos.ConfiguracionDTO;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Configuracion;
import com.base.rest.repositories.ConfiguracionRepository;
import com.base.rest.service.interfaces.ConfiguracionService;
import com.base.rest.utils.Converter;
import com.base.rest.utils.bd.FiltroTablasView;
import com.base.rest.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class ConfiguracionServiceImpl extends RepositoryServiceImpl<Configuracion, Integer> implements ConfiguracionService {

	private Converter<ConfiguracionDTO, Configuracion> converterEntity;
	
	private Converter<Configuracion, ConfiguracionDTO> converterDTO;
	
	public ConfiguracionServiceImpl(ConfiguracionRepository repository) {
		super(repository);
		converterEntity = new Converter<>(ConfiguracionDTO.class, Configuracion.class);
		converterDTO = new Converter<>(Configuracion.class, ConfiguracionDTO.class);
	}

	@Override
	public ConfiguracionDTO getByNombre(String nombre) {
		
		return (ConfiguracionDTO) converterDTO.toDTO(((ConfiguracionRepository)repository).getByNombre(nombre));
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
        return converterDTO.convertToResultTableDTO(((ConfiguracionRepository)repository).findAll(spec, pageable));
        
	}

	@Transactional
	@Override
	public void crear(ConfiguracionDTO configuracion) {

		save((Configuracion) converterEntity.toEntity(configuracion));
	}

	@Transactional
	@Override
	public void actualizar(Integer id, ConfiguracionDTO configuracion) {

		update(id, (Configuracion) converterEntity.toEntity(configuracion));
	}

	@Override
	public ConfiguracionDTO getById(Integer id) {
		
		return (ConfiguracionDTO) converterDTO.toDTO(findById(id));
	}

	@Transactional
	@Override
	public void borrar(Integer id) {
		
		deleteById(id);
	}
}
