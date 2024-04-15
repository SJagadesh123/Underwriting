package com.zettamine.mpa.ucm.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.zettamine.mpa.ucm.dto.SearchCriteriaDto;
import com.zettamine.mpa.ucm.dto.SearchResultDto;
import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.exception.CompanyAlreadyExists;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.mapper.UnderwritingCompanyMapper;
import com.zettamine.mpa.ucm.repository.SearchCriteriaRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingCompanyRepository;
import com.zettamine.mpa.ucm.utility.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnderwritingCompanyServiceImpl implements IUnderwritingCompanyService {

	private UnderwritingCompanyRepository underwritingCompanyRepository;
	private SearchCriteriaRepository searchCriteriaRepository;

	@Override
	public void save(UnderwritingCompanyDto underwritingCompanyDto)
			throws IllegalArgumentException, IllegalAccessException {

		String name = StringUtils.trimSpacesBetween(underwritingCompanyDto.getName());
		Optional<UnderwritingCompany> uwc = underwritingCompanyRepository.findByName(name.toUpperCase());

		if (uwc.isPresent()) {
			throw new CompanyAlreadyExists("Company exist with name " + name);
		}

		if (underwritingCompanyRepository.findByEmail(underwritingCompanyDto.getEmail()).isPresent()) {
			throw new DuplicationException("Company already exists with email : " + underwritingCompanyDto.getEmail());
		}

		if (underwritingCompanyRepository.findByPhone(underwritingCompanyDto.getPhone()).isPresent()) {
			throw new DuplicationException("Company already exists with phone : " + underwritingCompanyDto.getPhone());
		}

		toUpper(underwritingCompanyDto);

		UnderwritingCompany underwritingCompany = UnderwritingCompanyMapper.toEntity(underwritingCompanyDto,
				new UnderwritingCompany());
		underwritingCompanyRepository.save(underwritingCompany);

	}

	@Override
	public void update(Long uwcoId, UnderwritingCompanyDto underwritingCompanyDto)
			throws IllegalArgumentException, IllegalAccessException {

		UnderwritingCompany uwc = underwritingCompanyRepository.findById(uwcoId)
				.orElseThrow(() -> new ResourceNotFoundException("Company Details not found with Id : " + uwcoId));
		String name = StringUtils.trimSpacesBetween(underwritingCompanyDto.getName());
		Optional<UnderwritingCompany> optUwc = underwritingCompanyRepository.findByName(name.toUpperCase());

		if (optUwc.isPresent() && optUwc.get().getUwcoId() != uwcoId) {
			throw new DuplicationException("A company already present with name : " + name);
		}

		toUpper(underwritingCompanyDto);

		UnderwritingCompany underwritingCompany = UnderwritingCompanyMapper.toEntity(underwritingCompanyDto, uwc);
		underwritingCompany.setUwcoId(uwcoId);
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

	@Override
	public UnderwritingCompanyDto get(Long uwcoId) {

		UnderwritingCompany uwc = underwritingCompanyRepository.findById(uwcoId)
				.orElseThrow(() -> new ResourceNotFoundException("Company Details not found with Id : " + uwcoId));

		UnderwritingCompanyDto underwritingCompanyDto = UnderwritingCompanyMapper.toDto(uwc,
				new UnderwritingCompanyDto());

		return underwritingCompanyDto;
	}

	@Override
	public List<UnderwritingCompanyDto> getAll() {

		List<UnderwritingCompany> listOfUwc = underwritingCompanyRepository.findAll();

		if (listOfUwc.size() == 0) {
			throw new ResourceNotFoundException("There are no company present");
		}

		List<UnderwritingCompanyDto> underwritingCompanyDtos = new ArrayList<>();

		for (UnderwritingCompany company : listOfUwc) {
			UnderwritingCompanyDto underwritingCompanyDto = UnderwritingCompanyMapper.toDto(company,
					new UnderwritingCompanyDto());

			underwritingCompanyDtos.add(underwritingCompanyDto);
		}

		return underwritingCompanyDtos;
	}

	@Override
	public Set<SearchResultDto> getByCriteria(SearchCriteriaDto searchCriteriaDto) {

		return searchCriteriaRepository.getByCriteria(searchCriteriaDto);
	}

}