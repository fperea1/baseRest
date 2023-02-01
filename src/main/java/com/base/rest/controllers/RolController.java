package com.base.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.SelectDTO;
import com.base.rest.service.interfaces.RolService;

@RestController
@RequestMapping(Constantes.ROLES)
public class RolController {

	@Autowired
	private RolService service;
	
	@GetMapping(Constantes.FIND_FOR_SELECT)
    public ResponseEntity<List<SelectDTO>> findForSelect() {
		
		List<SelectDTO> roles = service.findForSelect();
		
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}
