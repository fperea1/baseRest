package com.base.rest.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.base.rest.entities.BaseEntity;
import com.base.rest.utils.bd.SearchCriteriaColumn;

public class BaseSpecificationsBuilder {

	private List<SearchCriteriaColumn> params;

    public BaseSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public Specification<BaseEntity> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<BaseEntity> result = new BaseSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new BaseSpecification(params.get(i)));
        }

        return result;

    }

    public BaseSpecificationsBuilder with(String nameColumn, Object value, String matchMode) {
        params.add(new SearchCriteriaColumn(nameColumn, value, matchMode));
        return this;
    }

    public final BaseSpecificationsBuilder with(SearchCriteriaColumn criteria) {
        this.params.add(criteria);
        return this;
    }

    public final BaseSpecificationsBuilder with(List<SearchCriteriaColumn> params) {
        this.params = params;
        return this;
    }

}
