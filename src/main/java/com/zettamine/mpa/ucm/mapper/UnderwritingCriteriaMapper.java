package com.zettamine.mpa.ucm.mapper;

import com.zettamine.mpa.ucm.dto.UnderwritingCriteriaDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCriteria;

public class UnderwritingCriteriaMapper {

	public static UnderwritingCriteriaDto toDto(UnderwritingCriteria entity, UnderwritingCriteriaDto dto) {

		dto.setCriteriaId(entity.getCriteriaId());
		dto.setCriteriaName(entity.getCriteriaName());
		dto.setNotes(entity.getNotes());

		return dto;
	}

	public static UnderwritingCriteria toEntity(UnderwritingCriteriaDto dto, UnderwritingCriteria entity) {

		entity.setCriteriaName(dto.getCriteriaName());
		entity.setNotes(dto.getNotes());

		return entity;
	}

}
