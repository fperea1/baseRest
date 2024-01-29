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
	
	private static final String MATCH_MODE = "matchMode";
	
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
			if (!node.path(VALUE).asText().contentEquals("null") 
					&& !node.path(MATCH_MODE).asText().contentEquals("enum")
					&& !node.path(MATCH_MODE).asText().contentEquals("select")) {
	            scc.setValue(node.path(VALUE).asText());
	            scc.setMatchMode(node.path(MATCH_MODE).asText());
	            list.add(scc);
			}
		}
 		return list;
	}
	
	public static String[] getFiltrosSelect(JsonNode filters, String field) {
		
		Iterator<String> it = filters.fieldNames();
		List<String> values = new ArrayList<>();
		while (it.hasNext()) {
			String column = it.next();
			if (field.equals(column)) {
				JsonNode node = filters.path(column);
				if (!node.path(VALUE).asText().contentEquals("null") && node.path(MATCH_MODE).asText().equals("select") ) {
					for (String s: node.findValuesAsText("nombre")) {
				        values.add(s);
					}
				}
			}
		}
		return values.toArray(new String[values.size()]);
	}
	
	public static String[] getFiltrosEnum(JsonNode filters, String field) {
		
		Iterator<String> it = filters.fieldNames();
		List<String> values = new ArrayList<>();
		while (it.hasNext()) {
			String column = it.next();
			if (field.equals(column)) {
				JsonNode node = filters.path(column);
				if (!node.path(VALUE).asText().contentEquals("null") && node.path(MATCH_MODE).asText().equals("enum") ) {
					for (String s: node.findValuesAsText("codigo")) {
						values.add(s);
					}
				}
			}
		}
 		return values.toArray(new String[values.size()]);
	}
}
