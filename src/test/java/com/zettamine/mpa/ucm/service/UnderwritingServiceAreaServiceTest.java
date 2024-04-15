package com.zettamine.mpa.ucm.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import com.zettamine.mpa.ucm.dto.ServiceAreaDto;
import com.zettamine.mpa.ucm.dto.UnderwritingServiceAreaDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.entities.UnderwritingServiceArea;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.repository.UnderwritingCompanyRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingServiceAreaRepository;

public class UnderwritingServiceAreaServiceTest {

	@Mock
	private UnderwritingServiceAreaRepository underwritingServiceAreaRepository;

	@Mock
	private UnderwritingCompanyRepository underwritingCompanyRepository;

	@InjectMocks
	private UnderwritingServiceAreaServiceImpl underwritingServiceAreaService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSave_Successful() {
		// Arrange
		UnderwritingServiceAreaDto underwritingServiceAreaDto = new UnderwritingServiceAreaDto();
		underwritingServiceAreaDto.setUnderwritingCompanyName("company name");
		// Initialize underwritingServiceAreaDto
		List<ServiceAreaDto> serviceAreaDto = new ArrayList<>();
		underwritingServiceAreaDto.setServiceArea(serviceAreaDto);
		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		// Initialize underwritingCompany

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.of(underwritingCompany));

		// Act
		assertDoesNotThrow(() -> underwritingServiceAreaService.save(underwritingServiceAreaDto));

		// Assert
		// Add assertions to verify the behavior
	}

	@Test
	public void testSave_CompanyNotFound() {
		// Arrange
		UnderwritingServiceAreaDto underwritingServiceAreaDto = new UnderwritingServiceAreaDto();
		underwritingServiceAreaDto.setUnderwritingCompanyName("company name");

		// Initialize underwritingServiceAreaDto

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.empty());

		// Act + Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> underwritingServiceAreaService.save(underwritingServiceAreaDto));
		// Add assertions to verify the exception message and behavior
	}

	

}
