package com.zettamine.mpa.ucm.service;

import java.util.List;

import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;

public interface IUnderwritingHistoryService {
	
	void save(UnderwritingHistoryDto underwritingHistoryDto) throws IllegalArgumentException, IllegalAccessException;
	
	void update(Long historyId, UnderwritingHistoryDto underwritingHistoryDto) throws IllegalArgumentException, IllegalAccessException;
	
	List<UnderwritingHistoryDto> getByUwcId(Long uwcId);
	
	UnderwritingHistoryDto getByLoanId(Integer loanId);
	
	List<UnderwritingHistoryDto> getAll();

}
