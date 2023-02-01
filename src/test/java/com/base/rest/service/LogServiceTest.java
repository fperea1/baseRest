package com.base.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.entities.Log;
import com.base.rest.service.interfaces.LogService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class LogServiceTest {
	
	@Autowired
	private LogService service;
	
	private String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";
	
	@Test
	@Order(1)
	void testSaveOk() {
		Log log = new Log();
		log.setUsername("Pruebas");
		log.setEntidad("Entidad");
		log.setAccion("Acción");
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		service.save(log);
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 1);
	}

	@Test
	@Order(2)
	void testFindByFilter() {
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 1);
	}
	
	@Test
	@Order(3)
	void testSaveUsernameMinSizeKo() {
		Log log = new Log();
		log.setUsername("Pr");
		log.setEntidad("Entidad");
		log.setAccion("Acción");
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_USERNAME_LOG_SIZE);
	}
	
	@Test
	@Order(4)
	void testSaveUsernameMaxSizeKo() {
		Log log = new Log();
		log.setUsername("Pr01234567890123456789012345678901234567890123456789");
		log.setEntidad("Entidad");
		log.setAccion("Acción");
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_USERNAME_LOG_SIZE);		
	}
	
	@Test
	@Order(5)
	void testSaveUsernameNullKo() {
		Log log = new Log();
		log.setUsername(null);
		log.setEntidad("Entidad");
		log.setAccion("Acción");
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_USERNAME_OBLIGATORIO);		
	}
	
	@Test
	@Order(6)
	void testSaveEntidadMinSizeKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad("");
		log.setAccion("Acción");
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_ENTIDAD_LOG_SIZE);	
		
	}
	
	@Test
	@Order(7)
	void testSaveEntidadMaxSizeKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad("En01234567890123456789012345678901234567890123456789");
		log.setAccion("Acción");
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_ENTIDAD_LOG_SIZE);			
	}
	
	@Test
	@Order(8)
	void testSaveEntidadNullKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad(null);
		log.setAccion("Acción");
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_ENTIDAD_OBLIGATORIO);	
		
	}
	
	@Test
	@Order(9)
	void testSaveAccionMinSizeKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad("Entidad 2");
		log.setAccion("");
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_ACCION_LOG_SIZE);		
	}
	
	@Test
	@Order(10)
	void testSaveAccionMaxSizeKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad("Entidad 2");
		log.setAccion("Ac01234567890123456789012345678901234567890123456789");
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_ACCION_LOG_SIZE);		
	}
	
	@Test
	@Order(11)
	void testSaveAccionNullKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad("Entidad 2");
		log.setAccion(null);
		log.setObservaciones("Observaciones");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_ACCION_OBLIGATORIO);	
	}
	
	@Test
	@Order(12)
	void testSaveObservacionesMinSizeKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad("Entidad 2");
		log.setAccion("Acción 2");
		log.setObservaciones("");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_OBSERVACIONES_LOG_SIZE);
	}
	
	@Test
	@Order(13)
	void testSaveObservacionesMaxSizeKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad("Entidad 2");
		log.setAccion("Acción 2");
		log.setObservaciones("Ob0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_OBSERVACIONES_LOG_SIZE);
	}
	
	@Test
	@Order(14)
	void testSaveObservacionesNullKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad("Entidad 2");
		log.setAccion("Acción 2");
		log.setObservaciones(null);
		log.setFecha(new Date());
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_OBSERVACIONES_OBLIGATORIO);
	}
	
	@Test
	@Order(15)
	void testSaveFechaNullKo() {
		Log log = new Log();
		log.setUsername("Pruebas 2");
		log.setEntidad("Entidad 2");
		log.setAccion("Acción 2");
		log.setObservaciones("Obligaciones 2");
		log.setFecha(null);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(log) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_FECHA_OBLIGATORIO);
	}

}
