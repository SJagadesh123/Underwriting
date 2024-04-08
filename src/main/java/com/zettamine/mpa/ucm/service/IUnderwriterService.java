package com.zettamine.mpa.ucm.service;

import java.util.List;

import com.zettamine.mpa.ucm.dto.UnderwriterDto;

public interface IUnderwriterService {

	void save(UnderwriterDto underwriterDto) throws IllegalArgumentException, IllegalAccessException;
	
	UnderwriterDto getByUnderwriterId(Long underwriterId);
	
	List<UnderwriterDto> getByUwcId(Long uwcId);
	
	List<UnderwriterDto> getAll();

	void update(Long underwriterId, UnderwriterDto underwriterDto) throws IllegalArgumentException, IllegalAccessException;
}
