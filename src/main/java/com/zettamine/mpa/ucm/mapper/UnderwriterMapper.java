package com.zettamine.mpa.ucm.mapper;

import com.zettamine.mpa.ucm.dto.UnderwriterDto;
import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;
import com.zettamine.mpa.ucm.entities.Underwriter;
import com.zettamine.mpa.ucm.entities.UnderwritingHistory;

public class UnderwriterMapper {

	public static UnderwriterDto toDto(Underwriter entity, UnderwriterDto dto) {

		dto.setUnderwriterId(entity.getUnderwriterId());
		dto.setAppraiserLicenceId(entity.getAppraiserLicenceId());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setEmail(entity.getEmail());
		dto.setPhone(entity.getPhone());
		dto.setUnderwritingCompanyName(entity.getUnderwritingCompany().getName());
		dto.setLiabilityInsurance(entity.getLiabilityInsurance());
		
		return dto;
	}

	public static Underwriter toEntity(UnderwriterDto dto, Underwriter entity) {

		entity.setAppraiserLicenceId(dto.getAppraiserLicenceId());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		entity.setPhone(dto.getPhone());
		entity.setLiabilityInsurance(dto.getLiabilityInsurance());
		
		return entity;
	}
}
