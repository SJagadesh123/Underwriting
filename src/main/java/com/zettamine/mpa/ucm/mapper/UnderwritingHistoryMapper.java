package com.zettamine.mpa.ucm.mapper;

import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.entities.UnderwritingHistory;

public class UnderwritingHistoryMapper {

	public static UnderwritingHistoryDto toDto(UnderwritingHistory entity, UnderwritingHistoryDto dto) {

		dto.setHistoryId(entity.getHistoryId());
		dto.setLoanId(entity.getLoanId());
		dto.setDecision(entity.getDecision());
		dto.setDecisionDate(entity.getDecisionDate());
		dto.setNotes(entity.getNotes());

		return dto;
	}

	public static UnderwritingHistory toEntity(UnderwritingHistoryDto dto, UnderwritingHistory entity) {

		entity.setHistoryId(dto.getHistoryId());
		entity.setLoanId(dto.getLoanId());
		entity.setDecision(dto.getDecision());
		entity.setDecisionDate(dto.getDecisionDate());
		entity.setNotes(dto.getNotes());
		
		return entity;
	}
}
