package com.zettamine.mpa.ucm.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zettamine.mpa.ucm.dto.ServiceAreaDto;
import com.zettamine.mpa.ucm.dto.UnderwritingServiceAreaDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.entities.UnderwritingServiceArea;
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
		Set<ServiceAreaDto> serviceAreaDto = new HashSet<>();
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

	@Test
	public void testGetByUwcId_Successful() {
		// Arrange
		Long uwcId = 1L;
		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		underwritingCompany.setUwcoId(uwcId);

		List<UnderwritingServiceArea> serviceAreas = new ArrayList<>();
		serviceAreas.add(new UnderwritingServiceArea());

		underwritingCompany.setServiceAreas(serviceAreas);
		// Initialize serviceAreas

		when(underwritingCompanyRepository.findById(uwcId)).thenReturn(Optional.of(underwritingCompany));

		// Act
		UnderwritingServiceAreaDto result = underwritingServiceAreaService.getByUwcId(uwcId);

		// Assert
		assertNotNull(result);
		assertEquals(underwritingCompany.getName(), result.getUnderwritingCompanyName());
		assertEquals(serviceAreas.size(), result.getServiceArea().size());
		// Add more assertions as needed
	}

	@Test
	public void testGetByUwcId_CompanyNotFound() {
		// Arrange
		Long uwcId = 1L;
		when(underwritingCompanyRepository.findById(uwcId)).thenReturn(Optional.empty());

		// Act + Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> underwritingServiceAreaService.getByUwcId(uwcId));
		// Add assertions to verify the exception message and behavior
	}

	@Test
	public void testGet_ValidServiceAreaId() {
		// Arrange
		Long serviceAreaId = 1L;
		UnderwritingServiceArea serviceArea = new UnderwritingServiceArea();
		serviceArea.setServiceAreaId(serviceAreaId);
		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		underwritingCompany.setName("Test Company");
		serviceArea.setUnderwritingCompany(underwritingCompany);

		when(underwritingServiceAreaRepository.findById(serviceAreaId)).thenReturn(Optional.of(serviceArea));

		// Act
		UnderwritingServiceAreaDto result = underwritingServiceAreaService.get(serviceAreaId);

		// Assert
		assertNotNull(result);
		assertEquals(underwritingCompany.getName(), result.getUnderwritingCompanyName());
		assertFalse(result.getServiceArea().isEmpty());
		// Add more assertions as needed
	}

	@Test
	public void testGet_InvalidServiceAreaId() {
		// Arrange
		Long invalidServiceAreaId = 999L; // Invalid ID
		when(underwritingServiceAreaRepository.findById(invalidServiceAreaId)).thenReturn(Optional.empty());

		// Act + Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> underwritingServiceAreaService.get(invalidServiceAreaId));
		// Add assertions to verify the exception message and behavior
	}

	@Test
	public void testGetAll_NoServiceAreas() {
		// Arrange
		when(underwritingServiceAreaRepository.findAll()).thenReturn(Collections.emptyList());

		// Act
		List<UnderwritingServiceAreaDto> result = underwritingServiceAreaService.getAll();

		// Assert
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetAll_WithServiceAreas() {
		// Arrange
		UnderwritingServiceArea serviceArea1 = new UnderwritingServiceArea();
		serviceArea1.setServiceAreaId(1L);
		UnderwritingCompany company1 = new UnderwritingCompany();
		company1.setName("Company 1");
		serviceArea1.setUnderwritingCompany(company1);

		UnderwritingServiceArea serviceArea2 = new UnderwritingServiceArea();
		serviceArea2.setServiceAreaId(2L);
		UnderwritingCompany company2 = new UnderwritingCompany();
		company2.setName("Company 2");
		serviceArea2.setUnderwritingCompany(company2);

		when(underwritingServiceAreaRepository.findAll()).thenReturn(Arrays.asList(serviceArea1, serviceArea2));

		// Act
		List<UnderwritingServiceAreaDto> result = underwritingServiceAreaService.getAll();

		// Assert
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(2, result.size());
		assertEquals("Company 1", result.get(0).getUnderwritingCompanyName());
		assertEquals("Company 2", result.get(1).getUnderwritingCompanyName());
		
	}

	
}
