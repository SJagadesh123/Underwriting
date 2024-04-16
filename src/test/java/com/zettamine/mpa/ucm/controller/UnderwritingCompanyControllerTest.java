package com.zettamine.mpa.ucm.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zettamine.mpa.ucm.constants.AppConstants;
import com.zettamine.mpa.ucm.dto.ResponseDto;
import com.zettamine.mpa.ucm.dto.SearchCriteriaDto;
import com.zettamine.mpa.ucm.dto.SearchResultDto;
import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.service.IUnderwritingCompanyService;

@AutoConfigureMockMvc
@SpringBootTest
public class UnderwritingCompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUnderwritingCompanyService underwritingCompanyService;

    @InjectMocks
    private UnderwritingCompanyController underwritingCompanyController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(underwritingCompanyController).build();
    }

    @Test
    void testSaveCompanyDetails() throws Exception {
        // Given
    	UnderwritingCompanyDto companyDto = new UnderwritingCompanyDto();
    	companyDto.setName("NewRez Mortgage Services");
    	companyDto.setAddress("456 Oak St");
    	companyDto.setCity("Shania Town");
    	companyDto.setState("NY");
    	companyDto.setZipcode("54321");
    	companyDto.setCountry("USA");
    	companyDto.setPhone("987-654-3210");
    	companyDto.setEmail("info@xyzmortgageservices.com");
    	companyDto.setWebsite("www.newrezmortgageservices.com");
    	companyDto.setNotes("Specializes in FHA and VA loan underwriting.");
    	companyDto.setUwClaimProcess("Comprehensive claim management, employing advanced technology, experienced professionals, and ethical practices to deliver optimal outcomes for all parties involved.");        
        
        // When
        doNothing().when(underwritingCompanyService).save(companyDto);

        // Then
        mockMvc.perform(post("/api/v1/underwriting/company/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_201));
    }
    
    @Test
    void testUpdateCompanyDetails() throws Exception {
        // Given
        Long uwcoId = 123L;
        UnderwritingCompanyDto companyDto = new UnderwritingCompanyDto();
    	companyDto.setName("NewRez Mortgage Services");
    	companyDto.setAddress("456 Oak St");
    	companyDto.setCity("Shania Town");
    	companyDto.setState("NY");
    	companyDto.setZipcode("54321");
    	companyDto.setCountry("USA");
    	companyDto.setPhone("987-654-3210");
    	companyDto.setEmail("info@xyzmortgageservices.com");
    	companyDto.setWebsite("www.newrezmortgageservices.com");
    	companyDto.setNotes("Specializes in FHA and VA loan underwriting.");
    	companyDto.setUwClaimProcess("Comprehensive claim management, employing advanced technology, experienced professionals, and ethical practices to deliver optimal outcomes for all parties involved.");        
        // Populate companyDto with necessary data
        
        // When
        doNothing().when(underwritingCompanyService).update(uwcoId, companyDto);

        // Then
        mockMvc.perform(put("/api/v1/underwriting/company/update/{uwcoId}", uwcoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_200));
    }
    
    
    @Test
    void testGetCompanyDetails() throws Exception {
        // Given
        Long uwcoId = 123L;
        UnderwritingCompanyDto companyDto = new UnderwritingCompanyDto();
        // Populate companyDto with necessary data
        
        // When
        when(underwritingCompanyService.get(uwcoId)).thenReturn(companyDto);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/company/fetch/{uwcoId}", uwcoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
          
    }
    
    
    @Test
    void testGetAllCompanyDetails() throws Exception {
        // Given
        List<UnderwritingCompanyDto> companyList = new ArrayList<>();
        // Populate companyList with necessary data
        
        // When
        when(underwritingCompanyService.getAll()).thenReturn(companyList);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/company/fetchAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Add more assertions based on the expected behavior
                .andExpect(jsonPath("$").isArray());
                // You can add more assertions to verify the content of the response
    }
    
    @Test
    void testGetBySearchCriteria() throws Exception {
        // Given
        SearchCriteriaDto searchCriteriaDto = new SearchCriteriaDto();
        // Populate searchCriteriaDto with necessary data
        
        Set<SearchResultDto> searchResults = new HashSet<>();
        // Populate searchResults with necessary data
        
        // When
        when(underwritingCompanyService.getByCriteria(searchCriteriaDto)).thenReturn(searchResults);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/company/fetch-by-criteria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchCriteriaDto)))
                .andExpect(status().isOk())
                // Add more assertions based on the expected behavior
                .andExpect(jsonPath("$").isArray());
                // You can add more assertions to verify the content of the response
    }
    
    
    
}
