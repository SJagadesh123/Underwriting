package com.zettamine.mpa.ucm.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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
        UnderwritingCompany underwritingCompany = mock(UnderwritingCompany.class);
        when(underwritingCompany.getName()).thenReturn("Company Name");

        UnderwritingHistory history1 = mock(UnderwritingHistory.class);
        when(history1.getUnderwritingCompany()).thenReturn(underwritingCompany);

        List<UnderwritingHistory> underwritingHistories = new ArrayList<>();
        underwritingHistories.add(history1);

        when(underwritingCompanyRepository.findById(uwcId)).thenReturn(Optional.of(underwritingCompany));
        when(underwritingCompany.getUnderwritingHistories()).thenReturn(underwritingHistories);

        // Act
        List<UnderwritingHistoryDto> result = underwritingHistoryService.getByUwcId(uwcId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Company Name", result.get(0).getUnderwritingCompanyName());
    }

    @Test
    public void testGetByUwcId_CompanyNotFound() {
        // Arrange
        Long uwcId = 1L;
        when(underwritingCompanyRepository.findById(uwcId)).thenReturn(Optional.empty());

        // Act + Assert
         assertThrows(ResourceNotFoundException.class,
                () -> underwritingHistoryService.getByUwcId(uwcId));
    }

    @Test
    public void testGetByLoanId_Successful() {
        // Arrange
        Integer loanId = 123;
        UnderwritingHistory underwritingHistory = new UnderwritingHistory();
        underwritingHistory.setLoanId(loanId);
        when(underwritingHistoryRepository.findByLoanId(loanId))
            .thenReturn(Optional.of(underwritingHistory));

        // Mock behavior of UnderwritingCompanyRepository
        UnderwritingCompany underwritingCompany = new UnderwritingCompany();
        underwritingCompany.setUwcoId(123l);
        underwritingHistory.setUnderwritingCompany(underwritingCompany);
        when(underwritingCompanyRepository.findById(underwritingHistory.getUnderwritingCompany().getUwcoId()))
            .thenReturn(Optional.of(underwritingCompany));

        // Act
        UnderwritingHistoryDto result = underwritingHistoryService.getByLoanId(loanId);

        // Assert
        assertEquals(underwritingHistory.getLoanId(), result.getLoanId());
        assertEquals(underwritingCompany.getName(), result.getUnderwritingCompanyName());
        // Add more assertions as needed
    }

    @Test
    public void testGetByLoanId_HistoryNotFound() {
        // Arrange
        Integer loanId = 456;
        when(underwritingHistoryRepository.findByLoanId(loanId))
            .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> underwritingHistoryService.getByLoanId(loanId));
    }
    
    @Test
    public void testGetAll() {
        // Arrange
        List<UnderwritingHistory> underwritingHistories = new ArrayList<>();
        UnderwritingHistory underwritingHistory = mock(UnderwritingHistory.class);
        when(underwritingHistory.getUnderwritingCompany()).thenReturn(mock(UnderwritingCompany.class));
        underwritingHistories.add(underwritingHistory);
        when(underwritingHistoryRepository.findAll()).thenReturn(underwritingHistories);

        // Mock behavior of UnderwritingCompanyRepository
        UnderwritingCompany underwritingCompany = mock(UnderwritingCompany.class);
        when(underwritingCompany.getUwcoId()).thenReturn(1L);
        when(underwritingCompanyRepository.findById(underwritingCompany.getUwcoId()))
            .thenReturn(Optional.of(underwritingCompany));

        // Act
        List<UnderwritingHistoryDto> result = underwritingHistoryService.getAll();

        // Assert
        assertEquals(underwritingHistories.size(), result.size());
        // Add more assertions as needed
    }
}
