
package com.zettamine.mpa.ucm.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zettamine.mpa.ucm.dto.UnderwritingCriteriaDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCriteria;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.mapper.UnderwritingCriteriaMapper;
import com.zettamine.mpa.ucm.repository.UnderwritingCriteriaRepository;
import com.zettamine.mpa.ucm.utility.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnderwritingCriteriaServiceImpl implements IUnderwritingCriteriaService {

	private UnderwritingCriteriaRepository underwritingCriteriaRepository;

	@Override
	public void save(UnderwritingCriteriaDto underwritingCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		String name = StringUtils.trimSpacesBetween(underwritingCriteriaDto.getCriteriaName());
		Optional<UnderwritingCriteria> criteria = underwritingCriteriaRepository.findByCriteriaName(name.toUpperCase());

		if (criteria.isPresent()) {
			throw new DuplicationException("Criteria exist with name " + name);
		}
		toUpper(underwritingCriteriaDto);

		toUpper(underwritingCriteriaDto);
		UnderwritingCriteria underwritingCriteria = UnderwritingCriteriaMapper.toEntity(underwritingCriteriaDto,
				new UnderwritingCriteria());

		underwritingCriteriaRepository.save(underwritingCriteria);
	}

	public void update(Long id, UnderwritingCriteriaDto underwritingCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		UnderwritingCriteria underwritingCriteria = underwritingCriteriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Criteria not found with Id : " + id));

		toUpper(underwritingCriteriaDto);

		UnderwritingCriteria entity = UnderwritingCriteriaMapper.toEntity(underwritingCriteriaDto,
				underwritingCriteria);

		entity.setCriteriaId(id);

		underwritingCriteriaRepository.save(underwritingCriteria);
	}

	@Override
	public UnderwritingCriteriaDto get(Long id) {

		UnderwritingCriteria underwritingCriteria = underwritingCriteriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Criteria not found with Id : " + id));

		UnderwritingCriteriaDto underwritingCriteriaDto = UnderwritingCriteriaMapper.toDto(underwritingCriteria,
				new UnderwritingCriteriaDto());

		return underwritingCriteriaDto;
	}

	@Override
	public List<UnderwritingCriteriaDto> getAll() {

		List<UnderwritingCriteria> underwritingCriteriaList = underwritingCriteriaRepository.findAll();

		List<UnderwritingCriteriaDto> underwritingCriteriaDtos = new ArrayList<>();

		for (UnderwritingCriteria underwritingCriteria : underwritingCriteriaList) {
			UnderwritingCriteriaDto dto = UnderwritingCriteriaMapper.toDto(underwritingCriteria,
					new UnderwritingCriteriaDto());
			underwritingCriteriaDtos.add(dto);
		}

		return underwritingCriteriaDtos;

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
