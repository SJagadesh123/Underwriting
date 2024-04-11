package com.zettamine.mpa.ucm.service;

import java.util.List;

import com.zettamine.mpa.ucm.dto.ServiceAreaDto;
import com.zettamine.mpa.ucm.dto.UnderwritingServiceAreaDto;

public interface IUnderwritingServiceAreaService {

	void save(UnderwritingServiceAreaDto underwritingServiceAreaDto) throws IllegalArgumentException, IllegalAccessException;
	
	void update(Long serviceAreaId, ServiceAreaDto serviceAreaDto) throws IllegalArgumentException, IllegalAccessException;
	
	UnderwritingServiceAreaDto getByUwcId(Long uwcId);
	
	UnderwritingServiceAreaDto get(Long serviceAreaId);
	
	List<UnderwritingServiceAreaDto> getAll();
}
