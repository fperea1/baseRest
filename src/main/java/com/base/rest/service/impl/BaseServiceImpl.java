package com.base.rest.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.base.rest.constant.Constantes;
import com.base.rest.entities.BaseEntity;
import com.base.rest.specification.BaseSpecificationsBuilder;
import com.base.rest.utils.bd.FiltroTablasView;
import com.base.rest.utils.bd.FiltrosUtils;
import com.base.rest.utils.bd.SearchCriteriaColumn;

public class BaseServiceImpl {

	protected Pageable getPageable(boolean exportar, FiltroTablasView filtro) {
		Sort sort = getSort(filtro);
		Pageable pageable = null;
		if (!exportar) {
			pageable = PageRequest.of((filtro.getFirst() > 0 ? filtro.getFirst() / filtro.getRows() : filtro.getFirst()), filtro.getRows(), sort);
		} else {
			pageable = PageRequest.of(0, Constantes.MAX_ROWS_XLSX, sort);
		}
		return pageable;
	}
	
	protected Sort getSort(FiltroTablasView filtro) {
		// en principio se ordenan de último a primero
		Sort sort = Sort.by("id").descending();
		// En caso de tener un orden específico se cambiaría
		if (filtro != null && filtro.getSortField() != null && !filtro.getSortField().isBlank()) {
			sort = (filtro.getSortOrder() > 0) ? Sort.by(filtro.getSortField()).ascending() : Sort.by(filtro.getSortField()).descending();
		}
		return sort;
	}

	protected Specification<BaseEntity> getSpecification(FiltroTablasView filtro) {
		BaseSpecificationsBuilder builder = new BaseSpecificationsBuilder();
		if (filtro.getFilters() != null && !filtro.getFilters().isEmpty()) {
			List<SearchCriteriaColumn> params = FiltrosUtils.getFiltrosColumns(filtro.getFilters());
			builder.with(params);
		}
        return builder.build();
	}
}
