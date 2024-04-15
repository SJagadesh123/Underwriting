package com.zettamine.mpa.ucm.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCompany;
import com.zettamine.mpa.ucm.exception.CompanyAlreadyExists;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.repository.SearchCriteriaRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingCompanyRepository;

public class UnderwritingCompanyServiceTest {

    @Mock
    private UnderwritingCompanyRepository underwritingCompanyRepository;
    
    @Mock
    private SearchCriteriaRepository searchCriteriaRepository;

    @InjectMocks
    private UnderwritingCompanyServiceImpl underwritingCompanyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveNewCompanySuccess() throws Exception {
        UnderwritingCompanyDto underwritingCompanyDto = new UnderwritingCompanyDto();
        underwritingCompanyDto.setName("Test Company");
        underwritingCompanyDto.setEmail("test@test.com");
        underwritingCompanyDto.setPhone("1234567890");
        
        when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(underwritingCompanyRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(underwritingCompanyRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        
        assertDoesNotThrow(() -> underwritingCompanyService.save(underwritingCompanyDto));
        
        verify(underwritingCompanyRepository, times(1)).save(any(UnderwritingCompany.class));
    }

    @Test
    public void testSaveDuplicateNameThrowsException() throws Exception {
        UnderwritingCompanyDto underwritingCompanyDto = new UnderwritingCompanyDto();
        underwritingCompanyDto.setName("Existing Company");
        when(underwritingCompanyRepository.findByName(anyString())).thenReturn(Optional.of(new UnderwritingCompany()));
        
        assertThrows(CompanyAlreadyExists.class, () -> underwritingCompanyService.save(underwritingCompanyDto));
    }

    @Test
    public void testSaveDuplicateEmailThrowsException() throws Exception {
        UnderwritingCompanyDto underwritingCompanyDto = new UnderwritingCompanyDto();
        underwritingCompanyDto.setName("Company");
        underwritingCompanyDto.setEmail("existing@test.com");
        when(underwritingCompanyRepository.findByEmail(anyString())).thenReturn(Optional.of(new UnderwritingCompany()));
        
        assertThrows(DuplicationException.class, () -> underwritingCompanyService.save(underwritingCompanyDto));
    }

    @Test
    public void testSaveDuplicatePhoneThrowsException() throws Exception {
        UnderwritingCompanyDto underwritingCompanyDto = new UnderwritingCompanyDto();
        underwritingCompanyDto.setName("Company");
        underwritingCompanyDto.setPhone("1234567890");
        when(underwritingCompanyRepository.findByPhone(anyString())).thenReturn(Optional.of(new UnderwritingCompany()));
        
        assertThrows(DuplicationException.class, () -> underwritingCompanyService.save(underwritingCompanyDto));
    }
    
    @Test
    public void testUpdate_CompanyExistsSuccess() throws Exception {
        Long uwcoId = 1L;
        UnderwritingCompanyDto underwritingCompanyDto = new UnderwritingCompanyDto();
        underwritingCompanyDto.setName("Updated Company");
        
        UnderwritingCompany existingCompany = new UnderwritingCompany();
        existingCompany.setUwcoId(uwcoId);
        
        when(underwritingCompanyRepository.findById(uwcoId)).thenReturn(Optional.of(existingCompany));
        when(underwritingCompanyRepository.findByName(any())).thenReturn(Optional.empty());
        
        assertDoesNotThrow(() -> underwritingCompanyService.update(uwcoId, underwritingCompanyDto));
        
        verify(underwritingCompanyRepository, times(1)).save(any(UnderwritingCompany.class));
    }

    @Test
    public void testUpdateDuplicateNameThrowsException() throws Exception {
        Long uwcoId = 1L;
        UnderwritingCompanyDto underwritingCompanyDto = new UnderwritingCompanyDto();
        underwritingCompanyDto.setName("Existing Company");
        
        UnderwritingCompany existingCompany = new UnderwritingCompany();
        existingCompany.setUwcoId(uwcoId);
        
        when(underwritingCompanyRepository.findById(uwcoId)).thenReturn(Optional.of(existingCompany));
        when(underwritingCompanyRepository.findByName(any())).thenReturn(Optional.of(new UnderwritingCompany()));
        
        assertThrows(DuplicationException.class, () -> underwritingCompanyService.update(uwcoId, underwritingCompanyDto));
    }

    @Test
    public void testUpdateCompanyNotFoundThrowsException() throws Exception {
        Long uwcoId = 1L;
        UnderwritingCompanyDto underwritingCompanyDto = new UnderwritingCompanyDto();
        underwritingCompanyDto.setName("Updated Company");
        
        when(underwritingCompanyRepository.findById(uwcoId)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> underwritingCompanyService.update(uwcoId, underwritingCompanyDto));
    }
    
    @Test
    public void testGetCompanyExistsReturnsDto() throws Exception {
        Long uwcoId = 1L;
        UnderwritingCompany existingCompany = new UnderwritingCompany();
        existingCompany.setUwcoId(uwcoId);
        
        when(underwritingCompanyRepository.findById(uwcoId)).thenReturn(Optional.of(existingCompany));
        
        UnderwritingCompanyDto result = underwritingCompanyService.get(uwcoId);
        
        assertNotNull(result);
    }

    @Test
    public void testGetCompanyNotFoundThrowsException() throws Exception {
        Long uwcoId = 1L;
        
        when(underwritingCompanyRepository.findById(uwcoId)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> underwritingCompanyService.get(uwcoId));
    }
    
    @Test
    public void testGetAll_CompaniesExist_ReturnsListOfDto() throws Exception {
        List<UnderwritingCompany> listOfCompanies = new ArrayList<>();
        listOfCompanies.add(new UnderwritingCompany());
        listOfCompanies.add(new UnderwritingCompany());
        
        when(underwritingCompanyRepository.findAll()).thenReturn(listOfCompanies);
        
        List<UnderwritingCompanyDto> result = underwritingCompanyService.getAll();
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(listOfCompanies.size(), result.size());
    }

   

}