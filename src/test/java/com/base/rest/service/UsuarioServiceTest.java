package com.base.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.base.rest.dtos.UsuarioDTO;
import com.base.rest.exceptions.ServiceException;
import com.base.rest.service.interfaces.UsuarioService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioServiceTest {
	
	@Autowired
	private UsuarioService service;
	
	private String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";

	@Test
	@Order(1)
	void testSaveOk() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre");
		usuario.setUsername("Username");
		usuario.setPassword("0123456789");
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		service.save(usuario);
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 1);
	}
	
	@Test
	@Order(2)
	void test() {
		ResultTableDTO result = service.findByFilter(filtro, false);
		assertTrue(result == null || result.getList().size() >= 1);
	}
	
	@Test
	@Order(3)
	void testSaveNombreSizeMinKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("");
		usuario.setUsername("Username");
		usuario.setPassword("0123456789");
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(usuario) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_NOMBRE_USUARIO_SIZE);
	}
	
	@Test
	@Order(4)
	void testSaveNombreSizeMaxKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Us01234567890123456789012345678901234567890123456789");
		usuario.setUsername("Username");
		usuario.setPassword("0123456789");
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(usuario) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_NOMBRE_USUARIO_SIZE);
	}
	
	@Test
	@Order(5)
	void testSaveNombreNullKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre(null);
		usuario.setUsername("Username");
		usuario.setPassword("0123456789");
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(usuario) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_NOMBRE_OBLIGATORIO);
	}
	
	@Test
	@Order(6)
	void testSaveUsernameSizeMinKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre 2");
		usuario.setUsername("Us");
		usuario.setPassword("0123456789");
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(usuario) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_USERNAME_USUARIO_SIZE);
	}
	
	@Test
	@Order(7)
	void testSaveUsernameSizeMaxKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre 2");
		usuario.setUsername("Us01234567890123456789012345678901234567890123456789");
		usuario.setPassword("0123456789");
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(usuario) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_USERNAME_USUARIO_SIZE);
	}
	
	@Test
	@Order(8)
	void testSaveUsernameNullKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre 2");
		usuario.setUsername(null);
		usuario.setPassword("0123456789");
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(usuario) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_USERNAME_OBLIGATORIO);
	}
	
	@Test
	@Order(9)
	void testSavePasswordSizeMinKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre 2");
		usuario.setUsername("Username2");
		usuario.setPassword("01");
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		ServiceException ex = assertThrows(ServiceException.class, () -> service.save(usuario) );
		assertTrue(ex != null);
		assertEquals(ex.getMessage(), Constantes.EXC_LIMITE_CARACTERES_PASSWORD);
	}
	
	@Test
	@Order(10)
	void testSavePasswordSizeMaxKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre 2");
		usuario.setUsername("Username2");
		usuario.setPassword("Pa0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		ServiceException ex = assertThrows(ServiceException.class, () -> service.save(usuario) );
		assertTrue(ex != null);
		assertEquals(ex.getMessage(), Constantes.EXC_LIMITE_CARACTERES_PASSWORD);	
	}
	
	@Test
	@Order(11)
	void testSavePasswordNullKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre 2");
		usuario.setUsername("Username2");
		usuario.setPassword(null);
		usuario.setEmail("frank.perea@yahoo.es");
		usuario.setActivo(true);
		ServiceException ex = assertThrows(ServiceException.class, () -> service.save(usuario) );
		assertTrue(ex != null);
		assertEquals(ex.getMessage(), Constantes.EXC_LIMITE_CARACTERES_PASSWORD);	
	}
	
	@Test
	@Order(12)
	void testSaveEmailFormatoKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre 2");
		usuario.setUsername("Username2");
		usuario.setPassword("01234567890");
		usuario.setEmail("frank.perea");
		usuario.setActivo(true);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(usuario) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_EMAIL_USUARIO_FORMATO);
	}
	
	@Test
	@Order(13)
	void testSaveEmailNullKo() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre 2");
		usuario.setUsername("Username2");
		usuario.setPassword("01234567890");
		usuario.setEmail(null);
		usuario.setActivo(true);
		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> service.save(usuario) );
		List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
		assertTrue(messages != null);
		assertEquals(messages.get(0), Constantes.VALIDATION_EMAIL_OBLIGATORIO);
	}
	
	@Test
	@Order(14)
	void testSaveActivoNullOk() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre("Nombre 2");
		usuario.setUsername("Username2");
		usuario.setPassword("01234567890");
		usuario.setEmail("frank2.perea@yahoo.es");
		usuario.setActivo(null);
		service.save(usuario);
		UsuarioDTO uBD = service.findByUsername("Username2");
		assertEquals(uBD.getActivo(), true);
	}
	
	@Test
	@Order(15)
	void testGetByIdOk() {
		assertNotNull(service.getById(1));
	}
	
	@Test
	@Order(16)
	void testGetByIdKo() {
		ServiceException ex = assertThrows(ServiceException.class, () -> service.getById(100) );
		assertNotNull(ex);
		assertTrue(ex.getMessage().equals(Constantes.EXC_NO_EXISTE_ENTIDAD));
	}
	
	@Test
	@Order(17)
	void testGetByUsernameOk() {
		assertNotNull(service.findByUsername("Username"));
	}
	
	@Test
	@Order(18)
	void testGetByUsernameKo() {
		assertNull(service.findByUsername("Desconocido"));
	}
	
	@Test
	@Order(19)
	void testDeactivateById() {
		UsuarioDTO u = service.findByUsername("Username");
		service.deactivate(u.getId());
		assertEquals(false, service.findByUsername("Username").getActivo());
	}
	
	@Test
	@Order(20)
	void testActivateById() {
		UsuarioDTO u = service.findByUsername("Username");
		service.activate(u.getId());
		assertEquals(true, service.findByUsername("Username").getActivo());
	}
	
	@Test
	@Order(21)
	void testDeleteByIdOk() {
		service.deleteById(1);
		ServiceException ex = assertThrows(ServiceException.class, () -> service.getById(1) );
		assertNotNull(ex);
		assertTrue(ex.getMessage().equals(Constantes.EXC_NO_EXISTE_ENTIDAD));
	}
	
	@Test
	@Order(22)
	void testDeleteByIdKo() {
		ServiceException ex = assertThrows(ServiceException.class, () -> service.deleteById(25) );
		assertNotNull(ex);
		assertTrue(ex.getMessage().equals(Constantes.EXC_NO_EXISTE_ENTIDAD));
	}

}
