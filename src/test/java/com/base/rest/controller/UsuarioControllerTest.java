package com.base.rest.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.AutenticacionDTO;
import com.base.rest.dtos.CambioPasswordDTO;
import com.base.rest.dtos.RolDTO;
import com.base.rest.dtos.UsuarioDTO;
import com.base.rest.exceptions.ServiceException;
import com.base.rest.utils.I18nUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class UsuarioControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private static String token;
	
	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	@Order(1) 
	void testGetToken() throws Exception {

		AutenticacionDTO a = getAutenticacion("administrador", "Administrador01");
		
		String requestJson = getJson(a);

		ResultActions response = mockMvc
		    .perform(post("/autenticacion/generate-token")
		    .contentType(APPLICATION_JSON_UTF8)
			.content(requestJson))
		    .andExpect(status().isOk());
		
		token = response.andReturn().getResponse().getContentAsString();

		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@Order(2) 
	void testFindAll() throws Exception {
		
		String filtro = "{\"first\":0,\"rows\":10,\"sortOrder\":1,\"filters\":{},\"globalFilter\":null}";
		mockMvc
		    .perform(get(Constantes.USUARIOS + Constantes.FIND_BY_FILTER)
		    .param("filtro", filtro)
		    .header("authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    //.andExpect(jsonPath("$.length()").value(1))
		    .andExpect(jsonPath("$.length()").isNotEmpty())
		    .andExpect(jsonPath("$.list[0].email").value("administrador@ezentis.com"));
		
		//System.out.println(response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	@Order(3) 
	void testSave() throws Exception {
		
		UsuarioDTO u = getUsuario("userTest", "nombre test", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(post("/usuarios/save")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isOk());	
	}
	
	@Test
	@Order(4) 
	void testSaveKoUsernameNull() throws Exception {
		
		UsuarioDTO u = getUsuario(null, "nombre test", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_USERNAME_OBLIGATORIO))));	
	}
	
	@Test
	@Order(5) 
	void testSaveKoUsernameMinSize() throws Exception {
		
		UsuarioDTO u = getUsuario("u", "nombre test", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_USERNAME_USUARIO_SIZE))));	
	}
	
	@Test
	@Order(6) 
	void testSaveKoUsernameMaxSize() throws Exception {
		
		UsuarioDTO u = getUsuario("abcdefghijkmnopqrstuvwxyz12345678901234567890123456", "nombre test", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_USERNAME_USUARIO_SIZE))));	
	}
	
	@Test
	@Order(7) 
	void testSaveKoPasswordNull() throws Exception {
		
		UsuarioDTO u = getUsuario("username", "nombre test", null, "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))	    
		.andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_LIMITE_CARACTERES_PASSWORD))));
		
	}
	
	@Test
	@Order(8) 
	void testSaveKoPasswordMinSize() throws Exception {
		
		UsuarioDTO u = getUsuario("username", "nombre test", "abc", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_LIMITE_CARACTERES_PASSWORD))));
		
	}
	
	@Test
	@Order(9) 
	void testSaveKoPasswordMaxSize() throws Exception {
		
		UsuarioDTO u = getUsuario("username", "nombre test", 
				"01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_LIMITE_CARACTERES_PASSWORD))));
		
	}
	
	@Test
	@Order(10) 
	void testSaveKoNombreNull() throws Exception {
		
		UsuarioDTO u = getUsuario("username", null, "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_NOMBRE_OBLIGATORIO))));	
	}
	
	@Test
	@Order(11) 
	void testSaveKoNombreMinSize() throws Exception {
		
		UsuarioDTO u = getUsuario("username", "", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_NOMBRE_USUARIO_SIZE))));	
	}
	
	@Test
	@Order(12) 
	void testSaveKoNombreMaxSize() throws Exception {
		
		UsuarioDTO u = getUsuario("username", "012345678901234567890123456789012345678901234567890", "passwordTest", "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_NOMBRE_USUARIO_SIZE))));	
	}
	
	@Test
	@Order(13) 
	void testSaveKoEmailNull() throws Exception {
		
		UsuarioDTO u = getUsuario("username", "nombre", "passwordTest", null);
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_EMAIL_OBLIGATORIO))));	
	}
	
	@Test
	@Order(14) 
	void testSaveKoEmailMalFormado() throws Exception {
		
		UsuarioDTO u = getUsuario("username", "nombre", "passwordTest", "email");
		
		String requestJson = getJson(u);
		
		mockMvc
	    .perform(post("/usuarios/save")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_EMAIL_USUARIO_FORMATO))));	
	}
	
	@Test
	@Order(15) 
	void testUpdateOk() throws Exception {
		
		ResultActions response = mockMvc
	    .perform(get("/usuarios/find")
	    .param("id", "2")
	    .header("authorization", "Bearer " + token));
		
		UsuarioDTO u = getObjectFromJson(response.andReturn().getResponse().getContentAsString());
		u.setActivo(false);
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(put("/usuarios/update/" + u.getId())
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isOk());	
	}
	
	@Test
	@Order(16) 
	void testUpdateUserNoExists() throws Exception {
		
		UsuarioDTO u = getUsuario("userTestNoUpdate", "nombre test", null, "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(put("/usuarios/update/" + 10)
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isConflict())
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_ACTIVO_OBLIGATORIO))))
			.andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.VALIDATION_FECHA_ALTA_OBLIGATORIO))));
		
	}
	

	@Test
	@Order(17) 
	void testMetodoNoExiste() throws Exception {
		
		UsuarioDTO u = getUsuario("userTestNoUpdate", "nombre test", null, "tes@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(put("/usuarios/noExisto")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isNotFound());
	}
	

	@Test
	@Order(18) 
	void testFindByIdOk() throws Exception {
		
		mockMvc
		    .perform(get("/usuarios/find")
		    .param("id", "1")
		    .header("authorization", "Bearer " + token))
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$[?(@.email === 'administrador@ezentis.com')]").exists());
	}
	
	@Test
	@Order(19) 
	void testFindByIdUserNoExists() throws Exception {
		
		mockMvc
	    .perform(get("/usuarios/find")
	    .param("id", "5")
	    .header("authorization", "Bearer " + token))
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_NO_EXISTE_ENTIDAD))));
	}
	
	@Test
	@Order(20) 
	void testDeleteById() throws Exception {
		
		UsuarioDTO u = getUsuario("delete", "delete test", "passwordTest", "delete@ezentis");
		
		String requestJson = getJson(u);
		
		mockMvc
		    .perform(post("/usuarios/save")
		    .contentType(APPLICATION_JSON_UTF8)
		    .content(requestJson)
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isOk());	
		
		mockMvc
	    .perform(delete("/usuarios/delete")
	    .param("id", "3")
	    .header("authorization", "Bearer " + token))
	    .andExpect(status().isOk());
	}
	
	@Test
	@Order(21) 
	void testDeleteByIdUserNoExists() throws Exception {
		
		mockMvc
		    .perform(delete("/usuarios/delete")
		    .param("id", "30")
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_NO_EXISTE_ENTIDAD))));
	}
	
	@Test
	@Order(22) 
	void testDeactivateById() throws Exception {
		
		mockMvc
		    .perform(put("/usuarios/deactivate")
    		.contentType(APPLICATION_JSON_UTF8)
    		.content("2")
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isOk());
	}
	
	@Test
	@Order(23) 
	void testDeactivateByIdUserNoExists() throws Exception {

		mockMvc
		    .perform(put("/usuarios/deactivate")
    		.contentType(APPLICATION_JSON_UTF8)
    		.content("20")
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_NO_EXISTE_ENTIDAD))));
	}
	
	@Test
	@Order(24) 
	void testActivateById() throws Exception {

		mockMvc
		    .perform(put("/usuarios/activate")
    		.contentType(APPLICATION_JSON_UTF8)
    		.content("2")
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isOk());
	}
	
	@Test
	@Order(25) 
	void testActivateByIdUserNoExists() throws Exception {
		
		mockMvc
		    .perform(put("/usuarios/activate")
    		.contentType(APPLICATION_JSON_UTF8)
    		.content("20")
		    .header("authorization", "Bearer " + token))
		    .andExpect(status().isBadRequest())
		    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
		    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_NO_EXISTE_ENTIDAD))));
	}
	
	@Test
	@Order(26) 
	void testPeticionSinToken() throws Exception {
		
		mockMvc
		    .perform(put("/usuarios/activate")
		    .param("id", "2"))
		    .andDo(print())
		    .andExpect(status().isUnauthorized());
		
	}
	
	@Test
	@Order(27) 
	void testCambioPasswordUserOk() throws Exception {
		
		CambioPasswordDTO c = getCambioPasswordDTO(1, "Administrador01", "passwordTest2", "passwordTest2");
		
		String requestJson = getJsonCambio(c);
		
		mockMvc
	    .perform(put("/contacto/cambioPassword")
		.contentType(APPLICATION_JSON_UTF8)
		.content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andExpect(status().isOk());
		
		// La volvemos a dejar igual
		
		c = getCambioPasswordDTO(1, "passwordTest2", "Administrador01", "Administrador01");
		
		requestJson = getJsonCambio(c);
		
		mockMvc
	    .perform(put("/contacto/cambioPassword")
		.contentType(APPLICATION_JSON_UTF8)
		.content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andExpect(status().isOk());
	}
	
	@Test
	@Order(28) 
	void testCambioPasswordUserKo() throws Exception {
		
		CambioPasswordDTO c = getCambioPasswordDTO(2, "erronea", "passwordTest", "passwordTest");
		
		String requestJson = getJsonCambio(c);
		
		mockMvc
	    .perform(put("/contacto/cambioPassword")
		.contentType(APPLICATION_JSON_UTF8)
		.content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_PASSWORD_ANT_ERRONEA))));
	}
	
	@Test
	@Order(29) 
	void testCambioPasswordAdminOk() throws Exception {
		
		CambioPasswordDTO c = getCambioPasswordDTO(2, "passwordTest", "passwordTest2", "passwordTest2");
		
		String requestJson = getJsonCambio(c);
		
		mockMvc
	    .perform(put("/usuarios/cambioPasswordAdmin")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andExpect(status().isOk());	
	}
	
	@Test
	@Order(30) 
	void testCambioPasswordAdminKo() throws Exception {
		
		CambioPasswordDTO c = getCambioPasswordDTO(2000, "bbbb", "bbbb", "bbbb");
		
		String requestJson = getJsonCambio(c);
		
		mockMvc
	    .perform(put("/usuarios/cambioPasswordAdmin")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_NO_EXISTE_ENTIDAD))));
	}

	@Test
	@Order(31) 
	void testCambioPasswordDiferentKo() throws Exception {
		
		CambioPasswordDTO c = getCambioPasswordDTO(1, "Administrador01", "passwordTest", "passwordTest3");
		
		String requestJson = getJsonCambio(c);
		    
		mockMvc
	    .perform(put("/contacto/cambioPassword")
		.contentType(APPLICATION_JSON_UTF8)
		.content(requestJson)
	    .header("authorization", "Bearer " + token))
	    .andExpect(status().isBadRequest())
	    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
	    .andExpect(content().string(containsString(I18nUtils.getMensaje(Constantes.EXC_PASSWORDS_DIFERENTES))));
		}

	private CambioPasswordDTO getCambioPasswordDTO(Integer id, String oldPassword, String newPassword, String newPassword2) {
		CambioPasswordDTO c = new CambioPasswordDTO();
		c.setId(id);
		c.setOldPassword(oldPassword);
		c.setNewPassword(newPassword);
		c.setNewPassword2(newPassword2);
		return c;
	}

	private UsuarioDTO getUsuario(String username, String nombre, String password, String email) {
		UsuarioDTO u = new UsuarioDTO();
		u.setUsername(username);
		u.setNombre(nombre);
		u.setPassword(password);
		u.setEmail(email);
		RolDTO rol = new RolDTO();
		rol.setId(1);
		Set<RolDTO> roles = new HashSet<RolDTO>();
		roles.add(rol);
		u.setRoles(roles);
		return u;
	}

	private String getJson(UsuarioDTO u) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(u);
		return requestJson;
	}
	
	private String getJsonCambio(CambioPasswordDTO u) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(u);
		return requestJson;
	}
	
	private UsuarioDTO getObjectFromJson(String s) throws JsonMappingException, JsonProcessingException {
	    ObjectReader jsonObjectReader = new ObjectMapper().readerFor(UsuarioDTO.class);
	    UsuarioDTO u = jsonObjectReader.readValue(s);
	    return u;
	}
	
	private AutenticacionDTO getAutenticacion(String username, String password) {
		AutenticacionDTO a = new AutenticacionDTO();
		a.setUsername(username);
		a.setPassword(password);
		return a;
	}

	private String getJson(AutenticacionDTO a) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(a);
		return requestJson;
	}
}
