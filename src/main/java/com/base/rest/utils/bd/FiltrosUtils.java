package com.base.rest.utils.bd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.base.rest.exceptions.JsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class FiltrosUtils {
	
	private FiltrosUtils() {}
	
	private static final String VALUE = "value";
	
	public static FiltroTablasView getFiltroByString(String filtro) {
		ObjectMapper objectMapper = new ObjectMapper();
		FiltroTablasView filtroDTO;
		try {
			filtroDTO = objectMapper.readValue(filtro, FiltroTablasView.class);
		} catch (JsonProcessingException e) {
			throw new JsonException();
		}	
		return filtroDTO;
	}
	
	public static FiltroSelectView getFiltroSelectByString(String filtro) {
		ObjectMapper objectMapper = new ObjectMapper();
		FiltroSelectView filtroDTO;
		try {
			filtroDTO = objectMapper.readValue(filtro, FiltroSelectView.class);
		} catch (JsonProcessingException e) {
			throw new JsonException();
		}	
		return filtroDTO;
	}

	public static List<SearchCriteriaColumn> getFiltrosColumns(JsonNode filters) {
		
		List<SearchCriteriaColumn> list = new ArrayList<>();
		Iterator<String> it = filters.fieldNames();
		while (it.hasNext()) {
			String column = it.next();
			SearchCriteriaColumn scc = new SearchCriteriaColumn();
			scc.setNameColumn(column);
			JsonNode node = filters.path(column);
			if (!node.path(VALUE).asText().contentEquals("null")) {
	            scc.setValue(node.path(VALUE).asText());
	            scc.setMatchMode(node.path("matchMode").asText());
	            list.add(scc);
			}
		}
 		return list;
	}
	
	public static List<SearchCriteriaColumn> getFiltrosSelect(JsonNode filters) {
		
		List<SearchCriteriaColumn> list = new ArrayList<>();
		Iterator<String> it = filters.fieldNames();
		while (it.hasNext()) {
			String column = it.next();
			JsonNode node = filters.path(column);
			if (!node.path(VALUE).asText().contentEquals("null") && node.path("matchMode").asText().equals("select") ) {
				for (String s: node.findValuesAsText("nombre")) {
					SearchCriteriaColumn scc = new SearchCriteriaColumn();
					scc.setValue(s);
					scc.setNameColumn(column);
			        list.add(scc);
				}
			}
		}
 		return list;
	}
}
