package com.base.rest.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.base.rest.constant.Constantes;

public final class Utilidades {
	
	private Utilidades() {}
	
	public static final DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ofPattern(Constantes.YYYY_MM_DD_HH_MM_SS_SSS);
	
	public static final DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern(Constantes.YYYY_MM_DD);
	
	public static LocalDateTime getDateFormat(String value) {
		if (value.length() > 10) {
			return getLocalDateTimeFormat(value);
		}
		return getLocalDateFormat(value);
	}
	
	private static LocalDateTime getLocalDateTimeFormat(String value) {
		return LocalDateTime.parse(value, formatterLocalDateTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	private static LocalDateTime getLocalDateFormat(String value) {
		return LocalDate.parse(value, formatterLocalDate).atStartOfDay();
	}

	public static LocalDateTime getDateFormatAddDay(String value) {
		return LocalDate.parse(value, formatterLocalDate).plusDays(1).atStartOfDay();
	}
}
