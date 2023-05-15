package com.base.rest.utils;

import com.base.rest.dtos.BaseDTO;
import com.base.rest.dtos.SelectDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utils {

	public static SelectDTO getSelectDTO(Integer id) {
		SelectDTO dto = new SelectDTO();
		dto.setId(id);
		return dto;
	}
	
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		return mapper;
	}
	
	public static String getJson(BaseDTO a) throws JsonProcessingException {
		ObjectMapper mapper = getObjectMapper();
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(a);
		return requestJson;
	}
	
}
