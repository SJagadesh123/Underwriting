package com.zettamine.mpa.ucm.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.zettamine.mpa.ucm.dto.UnderwriterDto;
import com.zettamine.mpa.ucm.entities.Underwriter;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.repository.UnderwriterRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingCompanyRepository;

public class UnderwriterServiceTest {

	@Mock
	private UnderwritingCompanyRepository underwritingCompanyRepository;

	@Mock
	private UnderwriterRepository underwriterRepository;

	@InjectMocks
	private UnderwriterServiceImpl underwriterService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSave_SuccessfulSave() {
		// Arrange
		UnderwriterDto underwriterDto = new UnderwriterDto();
		underwriterDto.setUnderwritingCompanyName("Company Name");
		underwriterDto.setEmail("test@test.com");
		underwriterDto.setPhone("1234567890");

		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		underwritingCompany.setName("COMPANY NAME");

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.of(underwritingCompany));
		when(underwriterRepository.findByAppraiserLicenceId(anyString())).thenReturn(Optional.empty());
		when(underwriterRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(underwriterRepository.findByPhone(anyString())).thenReturn(Optional.empty());

		// Act + Assert
		assertDoesNotThrow(() -> underwriterService.save(underwriterDto));
		verify(underwriterRepository, times(1)).save(any(Underwriter.class));
	}

	@Test
	public void testSave_CompanyNotFound() {

		UnderwriterDto underwriterDto = new UnderwriterDto();
		underwriterDto.setUnderwritingCompanyName("Company Name");

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.empty());

		// Act + Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> underwriterService.save(underwriterDto));
		assertEquals("Company doesnt exist with name Company Name", exception.getMessage());
	}

	@Test
	public void testSave_DuplicateAppraiserLicenceId() {
		// Arrange
		UnderwriterDto underwriterDto = new UnderwriterDto();
		underwriterDto.setUnderwritingCompanyName("Company Name");
		underwriterDto.setEmail("test@test.com");
		underwriterDto.setPhone("1234567890");

		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		underwritingCompany.setName("COMPANY NAME");

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.of(underwritingCompany));
		when(underwriterRepository.findByAppraiserLicenceId(anyString())).thenReturn(Optional.of(new Underwriter()));

		// Act + Assert
		DuplicationException exception = assertThrows(DuplicationException.class,
				() -> underwriterService.save(underwriterDto));
		assertEquals("Underwriter already exists with Appraiser Licence Id : " + underwriterDto.getAppraiserLicenceId(),
				exception.getMessage());
	}

	@Test
	public void testSave_DuplicateEmail() {
		// Arrange
		UnderwriterDto underwriterDto = new UnderwriterDto();
		underwriterDto.setUnderwritingCompanyName("Company Name");
		underwriterDto.setEmail("test@test.com");
		underwriterDto.setPhone("1234567890");

		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		underwritingCompany.setName("COMPANY NAME");

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.of(underwritingCompany));
		when(underwriterRepository.findByAppraiserLicenceId(anyString())).thenReturn(Optional.empty());
		when(underwriterRepository.findByEmail(anyString())).thenReturn(Optional.of(new Underwriter()));

		// Act + Assert
		DuplicationException exception = assertThrows(DuplicationException.class,
				() -> underwriterService.save(underwriterDto));
		assertEquals("Underwriter already exists with email : " + underwriterDto.getEmail(), exception.getMessage());
	}

	@Test
	public void testSave_DuplicatePhone() {
		// Arrange
		UnderwriterDto underwriterDto = new UnderwriterDto();
		underwriterDto.setUnderwritingCompanyName("Company Name");
		underwriterDto.setEmail("test@test.com");
		underwriterDto.setPhone("1234567890");

		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		underwritingCompany.setName("COMPANY NAME");

		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.of(underwritingCompany));
		when(underwriterRepository.findByAppraiserLicenceId(anyString())).thenReturn(Optional.empty());
		when(underwriterRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(underwriterRepository.findByPhone(anyString())).thenReturn(Optional.of(new Underwriter()));

		DuplicationException exception = assertThrows(DuplicationException.class,
				() -> underwriterService.save(underwriterDto));
		assertEquals("Underwriter already exists with phone : " + underwriterDto.getPhone(), exception.getMessage());
	}

	@Test
	public void testUpdate_SuccessfulUpdate() {
		// Arrange
		Long underwriterId = 1L;
		UnderwriterDto underwriterDto = new UnderwriterDto();
		underwriterDto.setUnderwritingCompanyName("Company Name");

		Underwriter underwriter = new Underwriter();
		underwriter.setUnderwriterId(underwriterId);

		when(underwriterRepository.findById(underwriterId)).thenReturn(Optional.of(underwriter));
		when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.empty());

		// Act + Assert
		assertDoesNotThrow(() -> underwriterService.update(underwriterId, underwriterDto));
		verify(underwriterRepository, times(1)).save(any(Underwriter.class));
	}

	@Test
	public void testUpdate_UnderwriterNotFound() {
		// Arrange
		Long underwriterId = 1L;
		when(underwriterRepository.findById(underwriterId)).thenReturn(Optional.empty());

		// Act + Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> underwriterService.update(underwriterId, new UnderwriterDto()));
		assertEquals("Underwriter not found with Id : " + underwriterId, exception.getMessage());
	}

	@Test
	public void testGetByUnderwriterId_Successful() {
		// Arrange
		Long underwriterId = 1L;
		Underwriter underwriter = new Underwriter();
		underwriter.setUnderwriterId(underwriterId);
		underwriter.setUnderwritingCompany(new UnderwritingCompany());

		when(underwriterRepository.findById(underwriterId)).thenReturn(Optional.of(underwriter));

		// Act
		UnderwriterDto result = underwriterService.getByUnderwriterId(underwriterId);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testGetByUnderwriterId_NotFound() {
		// Arrange
		Long underwriterId = 1L;
		when(underwriterRepository.findById(underwriterId)).thenReturn(Optional.empty());

		// Act + Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> underwriterService.getByUnderwriterId(underwriterId));
		assertEquals("Underwriter not found with Id : " + underwriterId, exception.getMessage());
	}

	@Test
	public void testGetByUwcId_Successful() {
		// Arrange
		Long uwcId = 1L;
		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		underwritingCompany.setUwcoId(uwcId);
		List<Underwriter> underwriters = new ArrayList<>();
		Underwriter underwriter = new Underwriter();
		underwriter.setUnderwritingCompany(underwritingCompany);
		underwriters.add(underwriter);
		underwritingCompany.setUnderwriters(underwriters);

		when(underwritingCompanyRepository.findById(uwcId)).thenReturn(Optional.of(underwritingCompany));

		// Act
		List<UnderwriterDto> result = underwriterService.getByUwcId(uwcId);

		// Assert
		assertNotNull(result);
		assertEquals(underwriters.size(), result.size());
	}

	@Test
	public void testGetByUwcId_CompanyNotFound() {
		// Arrange
		Long uwcId = 1L;
		when(underwritingCompanyRepository.findById(uwcId)).thenReturn(Optional.empty());

		// Act + Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> underwriterService.getByUwcId(uwcId));
		assertEquals("Company not found with Id : " + uwcId, exception.getMessage());
	}

	@Test
	public void testGetAll_Successful() {
		// Arrange
		List<Underwriter> underwriters = new ArrayList<>();
		UnderwritingCompany underwritingCompany = new UnderwritingCompany();
		Underwriter underwriter1 = new Underwriter();
		Underwriter underwriter2 = new Underwriter();
		underwriter1.setUnderwritingCompany(underwritingCompany);
		underwriter2.setUnderwritingCompany(underwritingCompany);
		underwriters.add(underwriter1);
		underwriters.add(underwriter2);

		when(underwriterRepository.findAll()).thenReturn(underwriters);

		// Act
		List<UnderwriterDto> result = underwriterService.getAll();

		// Assert
		assertNotNull(result);
		assertEquals(underwriters.size(), result.size());
	}

	@Test
	public void testGetAll_EmptyList() {
		// Arrange
		List<Underwriter> underwriters = new ArrayList<>();

		when(underwriterRepository.findAll()).thenReturn(underwriters);

		// Act
		List<UnderwriterDto> result = underwriterService.getAll();

		// Assert
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
}
