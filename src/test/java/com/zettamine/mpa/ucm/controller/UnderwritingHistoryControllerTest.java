package com.zettamine.mpa.ucm.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
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
import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;
import com.zettamine.mpa.ucm.service.IUnderwritingHistoryService;

@AutoConfigureMockMvc
@SpringBootTest
public class UnderwritingHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUnderwritingHistoryService underwritingHistoryService;

    @InjectMocks
    private UnderwritingHistoryController underwritingHistoryController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(underwritingHistoryController).build();
    }

    @Test
    void testSaveHistory() throws Exception {
        // Given
    	UnderwritingHistoryDto historyDto = new UnderwritingHistoryDto();
    	historyDto.setUnderwritingCompanyName("NewRez Mortgage Service");
    	historyDto.setLoanId(1001);
    	historyDto.setDecision("Approved");
    	historyDto.setDecisionDate(LocalDate.parse("2002-03-15"));
    	historyDto.setNotes("Loan approved based on satisfactory credit score and debt-to-income ratio.");

        
        
        // When
        doNothing().when(underwritingHistoryService).save(historyDto);

        // Then
        mockMvc.perform(post("/api/v1/underwriting/history/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historyDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_201));
    }
    
    @Test
    void testUpdateHistory() throws Exception {
        // Given
    	UnderwritingHistoryDto historyDto = new UnderwritingHistoryDto();
    	historyDto.setUnderwritingCompanyName("NewRez Mortgage Service");
    	historyDto.setLoanId(1001);
    	historyDto.setDecision("Approved");
    	historyDto.setDecisionDate(LocalDate.parse("2002-03-15"));
    	historyDto.setNotes("Loan approved based on satisfactory credit score and debt-to-income ratio.");
        // Populate historyDto with necessary data
        
        Long historyId = 123L;

        // When
        doNothing().when(underwritingHistoryService).update(historyId, historyDto);

        // Then
        mockMvc.perform(put("/api/v1/underwriting/history/update/{historyId}", historyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historyDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_200));
    }
    
    @Test
    void testFetchByUwcId() throws Exception {
        // Given
        Long uwcId = 123L;
        List<UnderwritingHistoryDto> historyList = new ArrayList<>();
        // Populate historyList with necessary data
        
        // When
        when(underwritingHistoryService.getByUwcId(uwcId)).thenReturn(historyList);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/history/fetch-by-uwcId/{uwcId}", uwcId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Add more assertions based on the expected behavior
                .andExpect(jsonPath("$").isArray());
                // You can add more assertions to verify the content of the response
    }
    
    @Test
    void testFetchByLoanId() throws Exception {
        // Given
        Integer loanId = 123456;
        UnderwritingHistoryDto historyDto = new UnderwritingHistoryDto();
        historyDto.setLoanId(loanId);
        // Set other necessary fields in historyDto
        
        // When
        when(underwritingHistoryService.getByLoanId(loanId)).thenReturn(historyDto);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/history/fetch-by-loanId/{loanId}", loanId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(loanId));
                // Add more assertions to verify other fields if needed
    }
    
    @Test
    void testFetchAll() throws Exception {
        // Given
        List<UnderwritingHistoryDto> historyList = new ArrayList<>();
        // Populate historyList with necessary data
        
        // When
        when(underwritingHistoryService.getAll()).thenReturn(historyList);

        // Then
        mockMvc.perform(get("/api/v1/underwriting/history/fetchAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Add more assertions based on the expected behavior
                .andExpect(jsonPath("$").isArray());
                // You can add more assertions to verify the content of the response
    }
    
    
    
    
}
