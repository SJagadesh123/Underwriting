package com.zettamine.mpa.ucm.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
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
import com.zettamine.mpa.ucm.constants.AppConstants;
import com.zettamine.mpa.ucm.dto.ResponseDto;
import com.zettamine.mpa.ucm.dto.UnderwriterDto;
import com.zettamine.mpa.ucm.service.IUnderwriterService;

@AutoConfigureMockMvc
@SpringBootTest
public class UnderwriterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUnderwriterService underwriterService;

    @InjectMocks
    private UnderwriterController underwriterController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(underwriterController).build();
    }

    @Test
    void testSaveUnderwriter() throws Exception {
        // Given
    	 UnderwriterDto underwriterDto = new UnderwriterDto(1L,"A123456780", "Carl", "Sagan", "NewRez Mortgage Service", "sagan@example.com", "555-123-4567",  "Comprehensive liability coverage to protect against potential risks, ensuring financial security and peace of mind.");
        // Populate underwriterDto with necessary data
        
        doNothing().when(underwriterService).save(any(UnderwriterDto.class));

        // Then
        mockMvc.perform(post("/api/v1/underwriting/underwriter/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(underwriterDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_201));
    }

    @Test
    void testUpdateUnderwriter() throws Exception {
        // Given
        Long underwriterId = 1L;
   	    UnderwriterDto underwriterDto = new UnderwriterDto(1L,"A123456780", "Carl", "Sagan", "NewRez Mortgage Service", "sagan@example.com", "555-123-4567",  "Comprehensive liability coverage to protect against potential risks, ensuring financial security and peace of mind.");

        // Populate underwriterDto with necessary data

        // When
        doNothing().when(underwriterService).update(eq(underwriterId), any(UnderwriterDto.class));

        // Then
        mockMvc.perform(put("/api/v1/underwriting/underwriter/update/{underwriterId}", underwriterId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(underwriterDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_200));
    }
    
    @Test
    void testFetchByUwcId() throws Exception {
        // Given
        Long uwcId = 123L;
        List<UnderwriterDto> underwriters = new ArrayList<>();
        // Populate underwriters list with necessary data
        
        // When
        when(underwriterService.getByUwcId(uwcId)).thenReturn(underwriters);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/underwriter/fetch-by-uwcId/{uwcId}", uwcId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Add more assertions based on the expected behavior
                .andExpect(jsonPath("$").isArray());
                // You can add more assertions to verify the content of the response
    }
    
    
    @Test
    void testFetch() throws Exception {
        // Given
        Long underwriterId = 123L;
        UnderwriterDto underwriterDto = new UnderwriterDto();
        // Populate underwriterDto with necessary data
        
        // When
        when(underwriterService.getByUnderwriterId(underwriterId)).thenReturn(underwriterDto);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/underwriter/fetch/{underwriterId}", underwriterId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                // Add more assertions based on the expected behavior
                
                // You can add more assertions to verify the content of the response
    }
    
    @Test
    void testFetchAll() throws Exception {
        // Given
        List<UnderwriterDto> underwriters = new ArrayList<>();
        // Populate underwriters list with necessary data
        
        // When
        when(underwriterService.getAll()).thenReturn(underwriters);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/underwriter/fetchAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Add more assertions based on the expected behavior
                .andExpect(jsonPath("$").isArray());
                // You can add more assertions to verify the content of the response
    }

    
}
