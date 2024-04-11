package com.zettamine.mpa.ucm.mapper;

import com.zettamine.mpa.ucm.dto.ServiceAreaDto;
import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;
import com.zettamine.mpa.ucm.entities.UnderwritingHistory;
import com.zettamine.mpa.ucm.entities.UnderwritingServiceArea;

public class UnderwritingServiceAreaMapper {

	public static ServiceAreaDto toDto(UnderwritingServiceArea entity, ServiceAreaDto dto) {

		dto.setServiceAreaId(entity.getServiceAreaId());
		dto.setCounty(entity.getCounty());
		dto.setCity(entity.getCity());
		dto.setState(entity.getState());
		dto.setZipcode(entity.getZipcode());
	

		return dto;
	}

	public static UnderwritingServiceArea toEntity(ServiceAreaDto dto, UnderwritingServiceArea entity) {

		entity.setCounty(dto.getCounty());
		entity.setCity(dto.getCity());
		entity.setState(dto.getState());
		entity.setZipcode(dto.getZipcode());
		

		return entity;
	}
}
