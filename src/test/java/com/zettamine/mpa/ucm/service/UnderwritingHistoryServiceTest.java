package com.zettamine.mpa.ucm.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.entities.UnderwritingHistory;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.repository.UnderwritingCompanyRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingHistoryRepository;

public class UnderwritingHistoryServiceTest {

	@Mock
	private UnderwritingCompanyRepository underwritingCompanyRepository;

	@Mock
	private UnderwritingHistoryRepository underwritingHistoryRepository;

	@InjectMocks
	private UnderwritingHistoryServiceImpl underwritingHistoryService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSave_SuccessfulSave() throws IllegalArgumentException, IllegalAccessException {
		UnderwritingHistoryDto underwritingHistoryDto = new UnderwritingHistoryDto();
		underwritingHistoryDto.setUnderwritingCompanyName("Company Name");
		underwritingHistoryDto.setLoanId(1);

		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		underwritingCompany.setName("COMPANY NAME");

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.of(underwritingCompany));
		when(underwritingHistoryRepository.findByLoanId(any())).thenReturn(Optional.empty());

		assertDoesNotThrow(() -> underwritingHistoryService.save(underwritingHistoryDto));

		verify(underwritingCompanyRepository, times(1)).findByName(anyString());
		verify(underwritingHistoryRepository, times(1)).findByLoanId(any());
		verify(underwritingHistoryRepository, times(1)).save(any(UnderwritingHistory.class));
	}

	@Test
	public void testSave_CompanyNotFound() {
		UnderwritingHistoryDto underwritingHistoryDto = new UnderwritingHistoryDto();
		underwritingHistoryDto.setUnderwritingCompanyName("Company Name");

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> underwritingHistoryService.save(underwritingHistoryDto));

		verify(underwritingCompanyRepository, times(1)).findByName(anyString());
		verify(underwritingHistoryRepository, never()).findByLoanId(any());
		verify(underwritingHistoryRepository, never()).save(any(UnderwritingHistory.class));
	}

	@Test
	public void testSave_HistoryAlreadyPresent() {
		UnderwritingHistoryDto underwritingHistoryDto = new UnderwritingHistoryDto();
		underwritingHistoryDto.setUnderwritingCompanyName("Company Name");
		underwritingHistoryDto.setLoanId(1);

		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		underwritingCompany.setName("COMPANY NAME");

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.of(underwritingCompany));
		when(underwritingHistoryRepository.findByLoanId(any())).thenReturn(Optional.of(new UnderwritingHistory()));

		assertThrows(DuplicationException.class, () -> underwritingHistoryService.save(underwritingHistoryDto));

		verify(underwritingCompanyRepository, times(1)).findByName(anyString());
		verify(underwritingHistoryRepository, times(1)).findByLoanId(any());
		verify(underwritingHistoryRepository, never()).save(any(UnderwritingHistory.class));
	}

	@Test
	public void testUpdate_SuccessfulUpdate() throws IllegalArgumentException, IllegalAccessException {
		// Arrange
		Long historyId = 1L;
		UnderwritingHistoryDto underwritingHistoryDto = new UnderwritingHistoryDto();
		underwritingHistoryDto.setUnderwritingCompanyName("Company Name");

		UnderwritingHistory existingHistory = new UnderwritingHistory();
		existingHistory.setHistoryId(historyId);
		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		existingHistory.setUnderwritingCompany(underwritingCompany);

		when(underwritingHistoryRepository.findById(historyId)).thenReturn(Optional.of(existingHistory));
		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.of(underwritingCompany));

		// Act + Assert
		assertDoesNotThrow(() -> underwritingHistoryService.update(historyId, underwritingHistoryDto));

		verify(underwritingHistoryRepository, times(1)).findById(historyId);
		verify(underwritingHistoryRepository, times(1)).save(any(UnderwritingHistory.class));
	}

	@Test
	public void testUpdate_HistoryNotFound() {
		// Arrange
		Long historyId = 1L;
		UnderwritingHistoryDto underwritingHistoryDto = new UnderwritingHistoryDto();

		when(underwritingHistoryRepository.findById(historyId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class,
				() -> underwritingHistoryService.update(historyId, underwritingHistoryDto));

		verify(underwritingHistoryRepository, times(1)).findById(historyId);
		verify(underwritingHistoryRepository, never()).save(any(UnderwritingHistory.class));
	}
	
	@Test
    public void testGetByUwcId_Successful() {
        // Arrange
        Long uwcId = 1L;
        UnderwritingCompany underwritingCompany = new UnderwritingCompany();
        underwritingCompany.setUwcoId(uwcId);

        List<UnderwritingHistory> underwritingHistories = new ArrayList<>();
        UnderwritingHistory history1 = new UnderwritingHistory();
        // set other properties of history1
        underwritingHistories.add(history1);
        // add more histories as needed

        when(underwritingCompanyRepository.findById(uwcId)).thenReturn(Optional.of(underwritingCompany));
        when(underwritingCompany.getUnderwritingHistories()).thenReturn(underwritingHistories);

        // Act
        List<UnderwritingHistoryDto> result = underwritingHistoryService.getByUwcId(uwcId);

        // Assert
        assertNotNull(result);
        assertEquals(underwritingHistories.size(), result.size());
        // Add more assertions to verify the correctness of the result
    }

    @Test
    public void testGetByUwcId_CompanyNotFound() {
        // Arrange
        Long uwcId = 1L;
        when(underwritingCompanyRepository.findById(uwcId)).thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> underwritingHistoryService.getByUwcId(uwcId));
        assertEquals("Company not found with Id : " + uwcId, exception.getMessage());
    }

}
