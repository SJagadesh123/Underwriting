package com.zettamine.mpa.ucm.service;

import java.lang.reflect.Field;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.entities.UnderwritingHistory;
import com.zettamine.mpa.ucm.exception.CompanyAlreadyExists;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.mapper.UnderwritingHistoryMapper;
import com.zettamine.mpa.ucm.repository.UnderwritingCompanyRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingHistoryRepository;
import com.zettamine.mpa.ucm.utility.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnderwritingHistoryServiceImpl implements IUnderwritingHistoryService {

	private UnderwritingHistoryRepository underwritingHistoryRepository;
	private UnderwritingCompanyRepository underwritingCompanyRepository;

	@Override
	public void save(UnderwritingHistoryDto underwritingHistoryDto)
			throws IllegalArgumentException, IllegalAccessException {

		String name = StringUtils.trimSpacesBetween(underwritingHistoryDto.getUnderwritingCompanyName());
		Optional<UnderwritingCompany> uwc = underwritingCompanyRepository.findByName(name.toUpperCase());

		if (uwc.isEmpty()) {
			throw new ResourceNotFoundException("Company doesnt exist with name " + name);
		}

		Optional<UnderwritingHistory> historyOpt = underwritingHistoryRepository
				.findByLoanId(underwritingHistoryDto.getLoanId());

		if (historyOpt.isPresent()) {
			throw new DuplicationException(
					"History already present for loan id : " + underwritingHistoryDto.getLoanId());
		}

		toUpper(underwritingHistoryDto);

		UnderwritingHistory underwritingHistory = UnderwritingHistoryMapper.toEntity(underwritingHistoryDto,
				new UnderwritingHistory());

		underwritingHistory.setUnderwritingCompany(uwc.get());

		underwritingHistoryRepository.save(underwritingHistory);

	}

	private static void toUpper(UnderwritingHistoryDto underwritingHistoryDto)
			throws IllegalArgumentException, IllegalAccessException {

		Field[] fields = underwritingHistoryDto.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			if (field.getType().equals(String.class)) {

				String value = (String) field.get(underwritingHistoryDto);

				if (value != null) {
					field.set(underwritingHistoryDto, StringUtils.trimSpacesBetween(value.toUpperCase()));
				}

			}

		}
	}

	@Override
	public void update(Long historyId, UnderwritingHistoryDto underwritingHistoryDto) {

		underwritingHistoryRepository.findById(historyId)
				.orElseThrow(() -> new ResourceNotFoundException("History not found with Id : " + historyId));

	}

}
