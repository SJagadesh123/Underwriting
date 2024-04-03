package com.zettamine.mpa.ucm.service;

import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;


public interface IUnderwritingCompanyService {

	void save(UnderwritingCompanyDto underwritingCompanyDto) throws IllegalArgumentException, IllegalAccessException;

	void update(Long uwcoId, UnderwritingCompanyDto underwritingCompanyDto) throws IllegalArgumentException, IllegalAccessException;
}