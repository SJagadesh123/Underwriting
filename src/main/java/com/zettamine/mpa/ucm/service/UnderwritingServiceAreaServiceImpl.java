package com.zettamine.mpa.ucm.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.zettamine.mpa.ucm.dto.ServiceAreaDto;
import com.zettamine.mpa.ucm.dto.UnderwritingServiceAreaDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.entities.UnderwritingServiceArea;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.mapper.UnderwritingServiceAreaMapper;
import com.zettamine.mpa.ucm.repository.UnderwritingCompanyRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingServiceAreaRepository;
import com.zettamine.mpa.ucm.utility.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnderwritingServiceAreaServiceImpl implements IUnderwritingServiceAreaService {

	private UnderwritingServiceAreaRepository underwritingServiceAreaRepository;
	private UnderwritingCompanyRepository underwritingCompanyRepository;

	@Override
	public void save(UnderwritingServiceAreaDto underwritingServiceAreaDto)
			throws IllegalArgumentException, IllegalAccessException {
		String name = StringUtils.trimSpacesBetween(underwritingServiceAreaDto.getUnderwritingCompanyName());
		Optional<UnderwritingCompany> uwc = underwritingCompanyRepository.findByName(name.toUpperCase());

		if (uwc.isEmpty()) {
			throw new ResourceNotFoundException("Company doesnt exist with name " + name);
		}
		UnderwritingCompany underwritingCompany = uwc.get();

		List<ServiceAreaDto> serviceAreas = underwritingServiceAreaDto.getServiceArea();

		List<UnderwritingServiceArea> underwritingServiceAreas = new ArrayList<>();

		for (ServiceAreaDto serviceArea : serviceAreas) {
			toUpper(serviceArea);

			UnderwritingServiceArea entity = UnderwritingServiceAreaMapper.toEntity(serviceArea,
					new UnderwritingServiceArea());
			entity.setUnderwritingCompany(underwritingCompany);

			List<UnderwritingServiceArea> list = underwritingServiceAreaRepository.findAll(Example.of(entity));

			if (list.size() > 0) {
				throw new DuplicationException("Duplicate Service areas are present, please check and re enter");
			}

			underwritingServiceAreas.add(entity);
		}

		underwritingCompany.setServiceAreas(underwritingServiceAreas);

		underwritingCompanyRepository.save(underwritingCompany);

	}

	private static void toUpper(ServiceAreaDto serviceAreaDto) throws IllegalArgumentException, IllegalAccessException {

		Field[] fields = serviceAreaDto.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			if (field.getType().equals(String.class)) {
				String fieldName = field.getName();

				if (!fieldName.equals("email") && !fieldName.equals("website")) {
					String value = (String) field.get(serviceAreaDto);

					if (value != null) {
						field.set(serviceAreaDto, StringUtils.trimSpacesBetween(value.toUpperCase()));
					}
				}
			}

		}
	}

	@Override
	public void update(Long serviceAreaId, ServiceAreaDto serviceAreaDto)
			throws IllegalArgumentException, IllegalAccessException {

		UnderwritingServiceArea serviceArea = underwritingServiceAreaRepository.findById(serviceAreaId)
				.orElseThrow(() -> new ResourceNotFoundException("Service Area not found with Id : " + serviceAreaId));

		UnderwritingCompany underwritingCompany = serviceArea.getUnderwritingCompany();

		toUpper(serviceAreaDto);

		
		UnderwritingServiceArea underwritingServiceArea = UnderwritingServiceAreaMapper.toEntity(serviceAreaDto,
				serviceArea);
		
		underwritingServiceArea.setServiceAreaId(null);
		underwritingServiceArea.setUnderwritingCompany(underwritingCompany);

		List<UnderwritingServiceArea> list = underwritingServiceAreaRepository.findAll(Example.of(underwritingServiceArea));

		if (list.size() > 0) {
			throw new DuplicationException("Duplicate Service areas are present, please check and re enter");
		}

		underwritingServiceArea.setServiceAreaId(serviceAreaId);

		underwritingServiceAreaRepository.save(underwritingServiceArea);
	}

	@Override
	public UnderwritingServiceAreaDto getByUwcId(Long uwcId) {

		UnderwritingCompany underwritingCompany = underwritingCompanyRepository.findById(uwcId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found with Id : " + uwcId));

		List<UnderwritingServiceArea> serviceAreas = underwritingCompany.getServiceAreas();

		List<ServiceAreaDto> serviceAreaDtos = new ArrayList<>();

		for (UnderwritingServiceArea serviceArea : serviceAreas) {
			serviceAreaDtos.add(UnderwritingServiceAreaMapper.toDto(serviceArea, new ServiceAreaDto()));
		}

		UnderwritingServiceAreaDto underwritingServiceAreaDto = new UnderwritingServiceAreaDto();

		underwritingServiceAreaDto.setUnderwritingCompanyName(underwritingCompany.getName());

		underwritingServiceAreaDto.setServiceArea(serviceAreaDtos);

		return underwritingServiceAreaDto;
	}

	@Override
	public UnderwritingServiceAreaDto get(Long serviceAreaId) {

		UnderwritingServiceArea serviceArea = underwritingServiceAreaRepository.findById(serviceAreaId)
				.orElseThrow(() -> new ResourceNotFoundException("Service Area not found with Id : " + serviceAreaId));

		List<ServiceAreaDto> serviceAreaDtos = new ArrayList<>();

		serviceAreaDtos.add(UnderwritingServiceAreaMapper.toDto(serviceArea, new ServiceAreaDto()));

		UnderwritingServiceAreaDto underwritingServiceAreaDto = new UnderwritingServiceAreaDto();

		underwritingServiceAreaDto.setUnderwritingCompanyName(serviceArea.getUnderwritingCompany().getName());

		underwritingServiceAreaDto.setServiceArea(serviceAreaDtos);

		return underwritingServiceAreaDto;
	}

	@Override
	public List<UnderwritingServiceAreaDto> getAll() {

		List<UnderwritingServiceArea> underwritingServiceAreas = underwritingServiceAreaRepository.findAll();

		List<UnderwritingServiceAreaDto> underwritingServiceAreaDtos = new ArrayList<>();

		for (UnderwritingServiceArea underwritingServiceArea : underwritingServiceAreas) {

			List<ServiceAreaDto> serviceAreaDtos = new ArrayList<>();

			serviceAreaDtos.add(UnderwritingServiceAreaMapper.toDto(underwritingServiceArea, new ServiceAreaDto()));

			UnderwritingServiceAreaDto underwritingServiceAreaDto = new UnderwritingServiceAreaDto();

			underwritingServiceAreaDto
					.setUnderwritingCompanyName(underwritingServiceArea.getUnderwritingCompany().getName());

			underwritingServiceAreaDto.setServiceArea(serviceAreaDtos);

			underwritingServiceAreaDtos.add(underwritingServiceAreaDto);

		}

		return underwritingServiceAreaDtos;
	}

}
