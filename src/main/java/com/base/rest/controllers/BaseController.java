package com.base.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.base.rest.constant.Constantes;
import com.base.rest.entities.Log;
import com.base.rest.service.interfaces.LogService;
import com.base.rest.utils.I18nUtils;

public class BaseController {
	
	@Autowired
	private LogService logService;

	protected String getCurrentUserName() {
		
		return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
	
	protected ResponseEntity<String> responseOperationCorrecta(String entidad, String accion, String observaciones) {
		
		logService.save(new Log(getCurrentUserName(), I18nUtils.getMensaje(entidad), I18nUtils.getMensaje(accion), observaciones));
		return new ResponseEntity<>(I18nUtils.getMensaje(Constantes.OPERACION_CORRECTA), HttpStatus.OK);
	}
}
