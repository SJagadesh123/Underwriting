package com.zettamine.mpa.ucm.service;

import java.lang.reflect.Field;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.exception.CompanyAlreadyExists;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.mapper.UnderwritingCompanyMapper;
import com.zettamine.mpa.ucm.repository.UnderwritingCompanyRepository;
import com.zettamine.mpa.ucm.utility.StringUtils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnderwritingCompanyServiceImpl implements IUnderwritingCompanyService {

	private UnderwritingCompanyRepository underwritingCompanyRepository;

	@Override
	public void save(UnderwritingCompanyDto underwritingCompanyDto)
			throws IllegalArgumentException, IllegalAccessException {

		String name = StringUtils.trimSpacesBetween(underwritingCompanyDto.getName());
		Optional<UnderwritingCompany> uwc = underwritingCompanyRepository.findByName(name.toUpperCase());

		if (uwc.isPresent()) {
			throw new CompanyAlreadyExists("Company exist with name " + name);
		}

		toUpper(underwritingCompanyDto);

		UnderwritingCompany underwritingCompany = UnderwritingCompanyMapper.toEntity(underwritingCompanyDto, new UnderwritingCompany());
		underwritingCompanyRepository.save(underwritingCompany);

	}

	@Override
	public void update(Long uwcoId, UnderwritingCompanyDto underwritingCompanyDto) throws IllegalArgumentException, IllegalAccessException {

		UnderwritingCompany uwc = underwritingCompanyRepository.findById(uwcoId)
				.orElseThrow(() -> new ResourceNotFoundException("Company Details not found with Id : " + uwcoId));

		toUpper(underwritingCompanyDto);

		UnderwritingCompany underwritingCompany = UnderwritingCompanyMapper.toEntity(underwritingCompanyDto, uwc);
		underwritingCompanyRepository.save(underwritingCompany);
	}

	private static void toUpper(UnderwritingCompanyDto underwritingCompanyDto)
			throws IllegalArgumentException, IllegalAccessException {

		Field[] fields = underwritingCompanyDto.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			if (field.getType().equals(String.class)) {
				String fieldName = field.getName();

				if (!fieldName.equals("email") && !fieldName.equals("website")) {
					String value = (String) field.get(underwritingCompanyDto);

					if (value != null) {
						field.set(underwritingCompanyDto, StringUtils.trimSpacesBetween(value.toUpperCase()));
					}
				}
			}

		}
	}

}
