package com.zettamine.mpa.ucm.service;

import java.util.List;
import java.util.Set;

import com.zettamine.mpa.ucm.dto.SearchCriteriaDto;
import com.zettamine.mpa.ucm.dto.SearchResultDto;
import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;


public interface IUnderwritingCompanyService {

	void save(UnderwritingCompanyDto underwritingCompanyDto) throws IllegalArgumentException, IllegalAccessException;

	void update(Long uwcoId, UnderwritingCompanyDto underwritingCompanyDto) throws IllegalArgumentException, IllegalAccessException;
	
	UnderwritingCompanyDto get(Long uwcoId);
	
	List<UnderwritingCompanyDto> getAll();
	
	Set<SearchResultDto> getByCriteria(SearchCriteriaDto searchCriteriaDto);
}
