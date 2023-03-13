package com.base.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.dtos.SelectDTO;
import com.base.rest.entities.Rol;
import com.base.rest.repositories.RolRepository;
import com.base.rest.service.interfaces.RolService;
import com.base.rest.utils.Converter;

@Service
@Transactional(readOnly = true)
public class RolServiceImpl implements RolService {

	@Autowired
	private RolRepository repository;
	
	private Converter<Rol, SelectDTO> toDTO;
	
	public RolServiceImpl() {
		super();
		toDTO = new Converter<>(Rol.class, SelectDTO.class);
	}

	@Override
	public List<SelectDTO> findForSelect() {
		
		Sort sort = Sort.by("nombre").ascending();
		
		return toDTO.convertListToSelectDTO(repository.findAll(sort));
	}

}
