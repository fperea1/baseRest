package com.base.rest.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.dtos.UsuarioDTO;
import com.base.rest.dtos.UsuarioListadoDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Rol;
import com.base.rest.entities.Usuario;
import com.base.rest.exceptions.ServiceException;
import com.base.rest.repositories.UsuarioRepository;
import com.base.rest.service.interfaces.UsuarioService;
import com.base.rest.utils.Converter;
import com.base.rest.utils.bd.FiltroTablasView;
import com.base.rest.utils.bd.FiltrosUtils;
import com.base.rest.utils.bd.SearchCriteriaColumn;

@Service
@Transactional(readOnly = true)
public class UsuarioServiceImpl extends RepositoryServiceImpl<Usuario, Integer> implements UsuarioService {

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	private Converter<UsuarioDTO, Usuario> converterEntity;
	
	private Converter<Usuario, UsuarioDTO> converterDTO;
	
	private Converter<Usuario, UsuarioListadoDTO> converterListadoDTO;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super(repository);
		converterEntity = new Converter<>(UsuarioDTO.class, Usuario.class);
		converterDTO = new Converter<>(Usuario.class, UsuarioDTO.class);
		converterListadoDTO = new Converter<>(Usuario.class, UsuarioListadoDTO.class);
	}

	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
		if (filtro.getFilters() != null && !filtro.getFilters().isEmpty()) {
			List<SearchCriteriaColumn> params = FiltrosUtils.getFiltrosSelect(filtro.getFilters());
			for (SearchCriteriaColumn scc: params) {
				spec = Specification.where(spec).or(hasRolConNombre(scc.getValue().toString()));
			}
		}
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return converterListadoDTO.convertToResultTableDTO(((UsuarioRepository)repository).findAll(spec, pageable));
	}
	
	private Specification<BaseEntity> hasRolConNombre(String nombre) {
	    return (root, query, criteriaBuilder) -> {
	        Join<Rol, Usuario> roles = root.join("roles");
	        return criteriaBuilder.equal(roles.get("nombre"), nombre);
	    };
	}

	@Transactional
	@Override
	public void crear(UsuarioDTO usuario) {

		validarPassword(usuario.getPassword(), null);
		usuario.setPassword(bcryptEncoder.encode(usuario.getPassword()));
		if (usuario.getId() == null) {
			usuario.setFechaAlta(new Date());
			usuario.setActivo(true);
		}
		save((Usuario) converterEntity.toEntity(usuario));
	}

	@Transactional
	@Override
	public void actualizar(Integer id, UsuarioDTO usuario) {

		update(id, (Usuario) converterEntity.toEntity(usuario));
	}

	@Override
	public UsuarioDTO getById(Integer id) {
		
		return (UsuarioDTO) converterDTO.toDTO(findById(id));
	}

	@Transactional
	@Override
	public void borrar(Integer id) {
		
		deleteById(id);
	}

	@Transactional
	@Override
	public void deactivate(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		((UsuarioRepository)repository).deactivate(new Date(), id);
	}

	@Transactional
	@Override
	public void activate(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		((UsuarioRepository)repository).activate(id);
	}
	
	private void validarPassword(String password, String newPassword2) {
		
		if (newPassword2 != null && !password.equals(newPassword2)) {
			throw new ServiceException(Constantes.EXC_PASSWORDS_DIFERENTES);
		}
		
		if (password == null || password.isBlank()
				|| password.length() < 10 || password.length() > 100) {
			throw new ServiceException(Constantes.EXC_LIMITE_CARACTERES_PASSWORD);
		}
	}

	@Transactional
	@Override
	public void cambioPasswordUser(Integer id, String username, String oldPassword, String newPassword, String newPassword2) {
		
		if (oldPassword == null) {
			throw new ServiceException(Constantes.EXC_PASSWORD_ANTERIOR_OBLIGATORIO);
		}
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, oldPassword));
		} catch (AuthenticationException e) {
			throw new ServiceException(Constantes.EXC_PASSWORD_ANT_ERRONEA);
		}
		validarPassword(newPassword, newPassword2);
		((UsuarioRepository)repository).changePassword(bcryptEncoder.encode(newPassword), id);
	}

	@Transactional
	@Override
	public void cambioPasswordAdmin(Integer id, String newPassword, String newPassword2) {
		
		UsuarioDTO u = getById(id);
		if (u == null) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		validarPassword(newPassword, newPassword2);
		((UsuarioRepository)repository).changePassword(bcryptEncoder.encode(newPassword), id);
	}

	@Override
	public UsuarioDTO findByUsername(String username) {
		
		return (UsuarioDTO) converterDTO.toDTO(((UsuarioRepository)repository).getByUsername(username));
	}

}
