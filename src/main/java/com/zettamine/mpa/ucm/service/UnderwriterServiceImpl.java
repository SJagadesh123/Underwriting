package com.zettamine.mpa.ucm.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zettamine.mpa.ucm.dto.UnderwriterDto;
import com.zettamine.mpa.ucm.entities.Underwriter;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.mapper.UnderwriterMapper;
import com.zettamine.mpa.ucm.repository.UnderwriterRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingCompanyRepository;
import com.zettamine.mpa.ucm.utility.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnderwriterServiceImpl implements IUnderwriterService {

	private UnderwritingCompanyRepository underwritingCompanyRepository;
	private UnderwriterRepository underwriterRepository;

	@Override
	public void save(UnderwriterDto underwriterDto) throws IllegalArgumentException, IllegalAccessException {
		String name = StringUtils.trimSpacesBetween(underwriterDto.getUnderwritingCompanyName());
		Optional<UnderwritingCompany> uwc = underwritingCompanyRepository.findByName(name.toUpperCase());

		if (uwc.isEmpty()) {
			throw new ResourceNotFoundException("Company doesnt exist with name " + name);
		}

		if (underwriterRepository.findByAppraiserLicenceId(underwriterDto.getEmail()).isPresent()) {
			throw new DuplicationException(
					"Underwriter already exists with Appraiser Licence Id : " + underwriterDto.getAppraiserLicenceId());
		}

		if (underwriterRepository.findByEmail(underwriterDto.getEmail()).isPresent()) {
			throw new DuplicationException("Underwriter already exists with email : " + underwriterDto.getEmail());
		}

		if (underwriterRepository.findByPhone(underwriterDto.getPhone()).isPresent()) {
			throw new DuplicationException("Underwriter already exists with phone : " + underwriterDto.getPhone());
		}

		toUpper(underwriterDto);

		Underwriter underwriter = UnderwriterMapper.toEntity(underwriterDto, new Underwriter());
		underwriter.setUnderwritingCompany(uwc.get());

		underwriterRepository.save(underwriter);
	}

	@Override
	public void update(Long underwriterId, UnderwriterDto underwriterDto)
			throws IllegalArgumentException, IllegalAccessException {
		Underwriter underwriter = underwriterRepository.findById(underwriterId)
				.orElseThrow(() -> new ResourceNotFoundException("Underwriter not found with Id : " + underwriterId));

		toUpper(underwriterDto);

		Underwriter underwriterEntity = UnderwriterMapper.toEntity(underwriterDto, new Underwriter());

		underwriterEntity.setUnderwriterId(underwriterId);
		underwriterEntity.setUnderwritingCompany(underwriter.getUnderwritingCompany());

		underwriterRepository.save(underwriterEntity);

	}

	@Override
	public UnderwriterDto getByUnderwriterId(Long underwriterId) {

		Underwriter underwriter = underwriterRepository.findById(underwriterId)
				.orElseThrow(() -> new ResourceNotFoundException("Underwriter not found with Id : " + underwriterId));

		UnderwriterDto underwriterDto = UnderwriterMapper.toDto(underwriter, new UnderwriterDto());

		return underwriterDto;
	}

	@Override
	public List<UnderwriterDto> getByUwcId(Long uwcId) {

		UnderwritingCompany underwritingCompany = underwritingCompanyRepository.findById(uwcId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found with Id : " + uwcId));

		List<Underwriter> underwriters = underwritingCompany.getUnderwriters();

		List<UnderwriterDto> underwritersDto = new ArrayList<>();

		for (Underwriter underwriter : underwriters) {
			underwritersDto.add(UnderwriterMapper.toDto(underwriter, new UnderwriterDto()));

		}

		return underwritersDto;
	}

	@Override
	public List<UnderwriterDto> getAll() {

		List<Underwriter> underwriters = underwriterRepository.findAll();
		List<UnderwriterDto> underwritersDto = new ArrayList<>();

		for (Underwriter underwriter : underwriters) {
			underwritersDto.add(UnderwriterMapper.toDto(underwriter, new UnderwriterDto()));

		}

		return underwritersDto;
	}

	private static void toUpper(UnderwriterDto underwriterDto) throws IllegalArgumentException, IllegalAccessException {

		Field[] fields = underwriterDto.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			if (field.getType().equals(String.class)) {
				String fieldName = field.getName();

				if (!fieldName.equals("email")) {
					String value = (String) field.get(underwriterDto);

					if (value != null) {
						field.set(underwriterDto, StringUtils.trimSpacesBetween(value.toUpperCase()));
					}
				}
			}

		}
	}

}
