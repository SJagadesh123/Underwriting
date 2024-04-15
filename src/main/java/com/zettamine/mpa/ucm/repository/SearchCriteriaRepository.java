package com.zettamine.mpa.ucm.repository;

import java.util.List;
import java.util.Set;

import com.zettamine.mpa.ucm.dto.SearchCriteriaDto;
import com.zettamine.mpa.ucm.dto.SearchResultDto;

public interface SearchCriteriaRepository {

	Set<SearchResultDto> getByCriteria(SearchCriteriaDto searchCriteria);
	
	Set<Integer> getLoanProductByCriteria(List<String> criteriaNames);

}
