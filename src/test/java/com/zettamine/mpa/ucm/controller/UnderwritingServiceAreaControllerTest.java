package com.zettamine.mpa.ucm.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import com.zettamine.mpa.ucm.dto.ServiceAreaDto;
import com.zettamine.mpa.ucm.dto.UnderwritingServiceAreaDto;
import com.zettamine.mpa.ucm.service.IUnderwritingServiceAreaService;

@AutoConfigureMockMvc
@SpringBootTest
public class UnderwritingServiceAreaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUnderwritingServiceAreaService serviceAreaService;

    @InjectMocks
    private UnderwritingServiceAreaController serviceAreaController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(serviceAreaController).build();
    }

    @Test
    void testSaveUnderwriter() throws Exception {
        // Given
    	
    	
    	UnderwritingServiceAreaDto serviceAreaDto = new UnderwritingServiceAreaDto();
    	serviceAreaDto.setUnderwritingCompanyName("NEWREZ MORTGAGE SERVICE");

    	// Initialize the serviceArea list if it's null
    	if (serviceAreaDto.getServiceArea() == null) {
    	    serviceAreaDto.setServiceArea(new HashSet<>());
    	}

    	ServiceAreaDto serviceArea = new ServiceAreaDto();
    	
    	serviceArea.setCounty("Monroe County");
    	serviceArea.setCity("Rochester");
    	serviceArea.setState("NY");
    	serviceArea.setZipcode("40945");

    	serviceAreaDto.getServiceArea().add(serviceArea);
    	
    	
    	
    	
        // Populate serviceAreaDto with necessary data
        
        // When
        doNothing().when(serviceAreaService).save(any(UnderwritingServiceAreaDto.class));

        // Then
        mockMvc.perform(post("/api/v1/underwriting/service-area/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceAreaDto)))
                .andExpect(status().isCreated())
                // Add more assertions based on the expected behavior
                .andExpect(jsonPath("$.statusCode").value("201"));
    }
    
    
//    @Test
//    void testUpdateUnderwriter() throws Exception {
//        // Given
//    	UnderwritingServiceAreaDto serviceAreaDto = new UnderwritingServiceAreaDto();
//    	serviceAreaDto.setUnderwritingCompanyName("NEWREZ MORTGAGE SERVICE");
//
//    	// Initialize the serviceArea list if it's null
//    	if (serviceAreaDto.getServiceArea() == null) {
//    	    serviceAreaDto.setServiceArea(new ArrayList<>());
//    	}
//
//    	ServiceAreaDto serviceArea = new ServiceAreaDto();
//    	
//    	serviceArea.setCounty("Monroe County");
//    	serviceArea.setCity("Rochester");
//    	serviceArea.setState("NY");
//    	serviceArea.setZipcode("40945");
//
//    	serviceAreaDto.getServiceArea().add(serviceArea);
//        // Populate serviceAreaDto with necessary data
//        
//        // When
//        doNothing().when(serviceAreaService).update(any(Long.class), any(ServiceAreaDto.class));
//
//        // Then
//        mockMvc.perform(put("/api/v1/underwriting/service-area/update/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(serviceAreaDto)))
//                .andExpect(status().isCreated())
//                // Add more assertions based on the expected behavior
//                .andExpect(jsonPath("$.statusCode").value("200"));
//    }
    
    
    @Test
    void testFetchByUwcId() throws Exception {
        // Given
        Long uwcId = 1L;
        UnderwritingServiceAreaDto serviceAreaDto = new UnderwritingServiceAreaDto();
        // Set up serviceAreaDto with necessary data
        
        // When
        when(serviceAreaService.getByUwcId(uwcId)).thenReturn(serviceAreaDto);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/service-area/fetch-by-uwcId/{uwcId}", uwcId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.underwritingCompanyName").value(serviceAreaDto.getUnderwritingCompanyName()));
    }
    
    @Test
    void testFetch() throws Exception {
        // Given
        Long id = 1L;
        UnderwritingServiceAreaDto serviceAreaDto = new UnderwritingServiceAreaDto();
        // Set up serviceAreaDto with necessary data
        
        // When
        when(serviceAreaService.get(id)).thenReturn(serviceAreaDto);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/service-area/fetch/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.underwritingCompanyName").value(serviceAreaDto.getUnderwritingCompanyName()));
    }
    
    @Test
    void testFetchAll() throws Exception {
        // Given
        List<UnderwritingServiceAreaDto> serviceAreaDtoList = new ArrayList<>();
        // Populate serviceAreaDtoList with necessary data
        
        // When
        when(serviceAreaService.getAll()).thenReturn(serviceAreaDtoList);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/service-area/fetchAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                
                // Add additional assertions if needed to validate the response content
    }
    
    
    
    
    
    
    
    
    
    
    
}
