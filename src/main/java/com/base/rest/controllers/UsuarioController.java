package com.base.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.CambioPasswordDTO;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.dtos.UsuarioDTO;
import com.base.rest.enums.reportes.TablaUsuariosEnum;
import com.base.rest.service.interfaces.UsuarioService;
import com.base.rest.utils.I18nUtils;
import com.base.rest.utils.POIUtils;
import com.base.rest.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.net.HttpHeaders;

@RestController
@RequestMapping(Constantes.USUARIOS)
public class UsuarioController extends BaseController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping(Constantes.FIND_BY_FILTER)
    public ResponseEntity<ResultTableDTO> findByFilter(@RequestParam String filtro) {
		
		ResultTableDTO resultTable = usuarioService.findByFilter(filtro, false);
		
        return new ResponseEntity<>(resultTable, HttpStatus.OK);
    }
	
	@GetMapping(value = Constantes.GET_REPORT_EXCEL)
	public ResponseEntity<Resource> getReportExcel(@RequestParam String filtro) {
		
		ResultTableDTO resultTable = usuarioService.findByFilter(filtro, true);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, Constantes.ATTACHMENTS_EXCEL)
				.contentType(MediaType.parseMediaType(Constantes.CONTENT_EXCEL))
				.cacheControl(CacheControl.noCache())
				.body(new ByteArrayResource(POIUtils.getReportExcel(resultTable.getList(), 
						TablaUsuariosEnum.values(), I18nUtils.getMensaje(Constantes.SHEET_USUARIOS))));
	}
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@Valid @RequestBody UsuarioDTO usuario) {
		usuarioService.crear(usuario);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.ALTA, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
    }
	
	@PutMapping(Constantes.UPDATE + "/{id}")
    public ResponseEntity<String> update(@RequestParam Integer id, @Valid @RequestBody UsuarioDTO usuario) {
		usuarioService.actualizar(id, usuario);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.EDICION, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
    }
	
	@JsonView(View.Public.class)
	@GetMapping(Constantes.FIND)
    public ResponseEntity<UsuarioDTO> findById(@RequestParam Integer id) {
		return new ResponseEntity<>(usuarioService.getById(id), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE)
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
		UsuarioDTO usuario = usuarioService.getById(id);
		usuarioService.borrar(id);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.BAJA, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
	}
	
	@PutMapping(Constantes.DEACTIVATE)
    public ResponseEntity<String> deactivateById(@RequestBody Integer id) {
		UsuarioDTO usuario = usuarioService.getById(id);
		usuarioService.deactivate(id);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.DESACTIVAR, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
	}
	
	@PutMapping(Constantes.ACTIVATE)
    public ResponseEntity<String> activateById(@RequestBody Integer id) {
		UsuarioDTO usuario = usuarioService.getById(id);
		usuarioService.activate(id);
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.ACTIVAR, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
	}
	
	@PutMapping(Constantes.CAMBIO_PASSWORD_ADMIN)
	public ResponseEntity<String> cambioPasswordAdmin(@RequestBody CambioPasswordDTO cambioPasswordDTO) {
		UsuarioDTO usuario = usuarioService.getById(cambioPasswordDTO.getId());
		usuarioService.cambioPasswordAdmin(cambioPasswordDTO.getId(), cambioPasswordDTO.getNewPassword(), cambioPasswordDTO.getNewPassword2());
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.CAMBIO_PASS_ADMIN, 
				I18nUtils.getMensaje(Constantes.USUARIO) + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
	}
}
