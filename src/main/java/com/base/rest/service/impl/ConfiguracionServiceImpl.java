package com.base.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.ConfiguracionDTO;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Configuracion;
import com.base.rest.exceptions.ServiceException;
import com.base.rest.repositories.ConfiguracionRepository;
import com.base.rest.service.interfaces.ConfiguracionService;
import com.base.rest.utils.Converter;
import com.base.rest.utils.bd.FiltroTablasView;
import com.base.rest.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class ConfiguracionServiceImpl extends BaseServiceImpl implements ConfiguracionService {

	@Autowired
	private ConfiguracionRepository configuracionRepository;
	
	private Converter<ConfiguracionDTO, Configuracion> converterEntity;
	
	private Converter<Configuracion, ConfiguracionDTO> converterDTO;

	public ConfiguracionServiceImpl() {
		super();
		converterEntity = new Converter<ConfiguracionDTO, Configuracion>(ConfiguracionDTO.class, Configuracion.class);
		converterDTO = new Converter<Configuracion, ConfiguracionDTO>(Configuracion.class, ConfiguracionDTO.class);
	}

	@Override
	public ConfiguracionDTO getByNombre(String nombre) {
		
		return (ConfiguracionDTO) converterDTO.toDTO(configuracionRepository.getByNombre(nombre));
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
        return converterDTO.convertToResultTableDTO(configuracionRepository.findAll(spec, pageable));
        
	}

	@Transactional
	@Override
	public void save(ConfiguracionDTO configuracion) {

		configuracionRepository.save((Configuracion) converterEntity.toEntity(configuracion));
	}

	@Transactional
	@Override
	public void update(ConfiguracionDTO configuracion) {

		configuracionRepository.save((Configuracion) converterEntity.toEntity(configuracion));
	}

	@Override
	public ConfiguracionDTO getById(Integer id) {
		
		if (!configuracionRepository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return (ConfiguracionDTO) converterDTO.toDTO(configuracionRepository.findById(id).orElse(null));
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		
		if (!configuracionRepository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		configuracionRepository.deleteById(id);
	}
}
