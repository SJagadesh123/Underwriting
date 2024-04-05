package com.zettamine.mpa.ucm.service;

import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;

public interface IUnderwritingHistoryService {
	
	void save(UnderwritingHistoryDto underwritingHistoryDto) throws IllegalArgumentException, IllegalAccessException;
	
	void update(Long historyId, UnderwritingHistoryDto underwritingHistoryDto);

}
