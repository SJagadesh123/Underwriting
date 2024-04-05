package com.zettamine.mpa.ucm.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.entities.UnderwritingHistory;
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
	public void update(Long historyId, UnderwritingHistoryDto underwritingHistoryDto)
			throws IllegalArgumentException, IllegalAccessException {

		UnderwritingHistory history = underwritingHistoryRepository.findById(historyId)
				.orElseThrow(() -> new ResourceNotFoundException("History not found with Id : " + historyId));

		toUpper(underwritingHistoryDto);

		UnderwritingHistory underwritingHistory = UnderwritingHistoryMapper.toEntity(underwritingHistoryDto,
				new UnderwritingHistory());

		underwritingHistory.setHistoryId(historyId);
		underwritingHistory.setUnderwritingCompany(history.getUnderwritingCompany());
		underwritingHistoryRepository.save(underwritingHistory);

	}

	@Override
	public List<UnderwritingHistoryDto> getByUwcId(Long uwcId) {

		UnderwritingCompany underwritingCompany = underwritingCompanyRepository.findById(uwcId)
				.orElseThrow(() -> new ResourceNotFoundException("Comapny not found with Id : " + uwcId));

		List<UnderwritingHistory> underwritingHistories = underwritingCompany.getUnderwritingHistories();

		List<UnderwritingHistoryDto> underwritingHistoryDto = new ArrayList<>();

		for (UnderwritingHistory history : underwritingHistories) {
			UnderwritingHistoryDto dto = UnderwritingHistoryMapper.toDto(history, new UnderwritingHistoryDto());
			dto.setUnderwritingCompanyName(history.getUnderwritingCompany().getName());

			underwritingHistoryDto.add(dto);
		}

		return underwritingHistoryDto;

	}

	@Override
	public UnderwritingHistoryDto getByLoanId(Integer loanId) {

		UnderwritingHistory underwritingHistory = underwritingHistoryRepository.findByLoanId(loanId)
				.orElseThrow(() -> new ResourceNotFoundException("History not found for loanId : " + loanId));
				

		UnderwritingHistoryDto underwritingHistoryDto = UnderwritingHistoryMapper.toDto(underwritingHistory, new UnderwritingHistoryDto());
		
		underwritingHistoryDto.setUnderwritingCompanyName(underwritingHistory.getUnderwritingCompany().getName());
		
		return underwritingHistoryDto;

	}

	@Override
	public List<UnderwritingHistoryDto> getAll() {
		
		List<UnderwritingHistory> underwritingHistories = underwritingHistoryRepository.findAll();
		
		List<UnderwritingHistoryDto> underwritingHistoryDto = new ArrayList<>();

		for (UnderwritingHistory history : underwritingHistories) {
			UnderwritingHistoryDto dto = UnderwritingHistoryMapper.toDto(history, new UnderwritingHistoryDto());
			dto.setUnderwritingCompanyName(history.getUnderwritingCompany().getName());

			underwritingHistoryDto.add(dto);
		}
		
		return underwritingHistoryDto;

	}

}
