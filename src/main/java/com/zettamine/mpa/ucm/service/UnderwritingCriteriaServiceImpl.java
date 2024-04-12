package com.zettamine.mpa.ucm.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.dto.UnderwritingCriteriaDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCriteria;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.repository.UnderwritingCriteriaRepository;
import com.zettamine.mpa.ucm.utility.StringUtils;

public class UnderwritingCriteriaServiceImpl implements IUnderwritingCriteriaService {

	private UnderwritingCriteriaRepository underwritingCriteriaRepository;

	@Override
	public void save(UnderwritingCriteriaDto underwritingCriteriaDto) throws IllegalArgumentException, IllegalAccessException {

		String name = StringUtils.trimSpacesBetween(underwritingCriteriaDto.getCriteriaName());
		Optional<UnderwritingCriteria> criteria = underwritingCriteriaRepository.findByName(name.toUpperCase());

		if (criteria.isPresent()) {
			throw new DuplicationException("Criteria exist with name " + name);
		}
		
		toUpper(underwritingCriteriaDto);

	}

	@Override
	public void update(Long id, UnderwritingCriteriaDto underwritingCriteriaDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public UnderwritingCriteriaDto get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnderwritingCriteriaDto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void toUpper(UnderwritingCriteriaDto underwritingCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		Field[] fields = underwritingCriteriaDto.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			if (field.getType().equals(String.class)) {

				String value = (String) field.get(underwritingCriteriaDto);

				if (value != null) {
					field.set(underwritingCriteriaDto, StringUtils.trimSpacesBetween(value.toUpperCase()));

				}
			}

		}
	}
}
