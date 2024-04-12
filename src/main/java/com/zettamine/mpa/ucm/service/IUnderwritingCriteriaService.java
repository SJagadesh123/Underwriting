package com.zettamine.mpa.ucm.service;

import java.util.List;

import com.zettamine.mpa.ucm.dto.UnderwritingCriteriaDto;

public interface IUnderwritingCriteriaService {

	void save(UnderwritingCriteriaDto underwritingCriteriaDto);
	
	void update(Long id, UnderwritingCriteriaDto underwritingCriteriaDto);
	
	UnderwritingCriteriaDto get(Long id);
	
	List<UnderwritingCriteriaDto> getAll();
}
