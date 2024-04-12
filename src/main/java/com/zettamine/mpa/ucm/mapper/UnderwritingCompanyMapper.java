package com.zettamine.mpa.ucm.mapper;

import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;

public class UnderwritingCompanyMapper {
	public static UnderwritingCompanyDto toDto(UnderwritingCompany entity, UnderwritingCompanyDto dto) {

		dto.setName(entity.getName());
		dto.setAddress(entity.getAddress());
		dto.setCity(entity.getCity());
		dto.setState(entity.getState());
		dto.setZipcode(entity.getZipcode());
		dto.setCountry(entity.getCountry());
		dto.setPhone(entity.getPhone());
		dto.setEmail(entity.getEmail());
		dto.setWebsite(entity.getWebsite());
		dto.setNotes(entity.getNotes());
		dto.setUwClaimProcess(entity.getUwClaimProcess());

		return dto;
	}

	public static UnderwritingCompany toEntity(UnderwritingCompanyDto dto, UnderwritingCompany entity) {

		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());
		entity.setCity(dto.getCity());
		entity.setState(dto.getState());
		entity.setZipcode(dto.getZipcode());
		entity.setCountry(dto.getCountry());
		entity.setPhone(dto.getPhone());
		entity.setEmail(dto.getEmail());
		entity.setWebsite(dto.getWebsite());
		entity.setNotes(dto.getNotes());
		entity.setUwClaimProcess(dto.getUwClaimProcess());

		return entity;
	}
}
