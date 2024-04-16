package com.zettamine.mpa.ucm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zettamine.mpa.ucm.constants.AppConstants;
import com.zettamine.mpa.ucm.dto.LoanProductCriteriaDto;
import com.zettamine.mpa.ucm.dto.UnderwritingCriteriaDto;
import com.zettamine.mpa.ucm.service.IUnderwritingCriteriaService;

@AutoConfigureMockMvc
@SpringBootTest
public class UnderwritingCriteriaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private IUnderwritingCriteriaService underwritingCriteriaService;

	@InjectMocks
	private UnderwritingCriteriaController underwritingCriteriaController;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(underwritingCriteriaController).build();
	}

	@Test
	void testSaveCriteria() throws Exception {
		// Given
		UnderwritingCriteriaDto criteriaDto = new UnderwritingCriteriaDto();

		criteriaDto.setCriteriaName("Credit Score Requirement");
		criteriaDto.setNotes("Minimum required credit score for loan approval.");

		// When
		doNothing().when(underwritingCriteriaService).save(criteriaDto);

		// Then
		mockMvc.perform(post("/api/v1/underwriting/criteria/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(criteriaDto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_201))
				.andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_201));
	}

	@Test
	void testUpdateCriteria() throws Exception {
		// Given
		Long criteriaId = 123L;
		UnderwritingCriteriaDto criteriaDto = new UnderwritingCriteriaDto();
		criteriaDto.setCriteriaId(criteriaId);
		criteriaDto.setCriteriaName("Updated Credit Score Requirement");
		criteriaDto.setNotes("Updated minimum required credit score for loan approval.");

		// When
		doNothing().when(underwritingCriteriaService).update(criteriaId, criteriaDto);

		// Then
		mockMvc.perform(put("/api/v1/underwriting/criteria/update/{id}", criteriaId)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(criteriaDto)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_200))
				.andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_200));
	}

	@Test
	void testFetchCriteria() throws Exception {
		// Given
		Long criteriaId = 123L;
		UnderwritingCriteriaDto criteriaDto = new UnderwritingCriteriaDto();
		// Populate criteriaDto with necessary data

		// When
		when(underwritingCriteriaService.get(criteriaId)).thenReturn(criteriaDto);

		// Then
		mockMvc.perform(
				get("/api/v1/underwriting/criteria/fetch/{id}", criteriaId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testFetchAllCriteria() throws Exception {
		// Given
		List<UnderwritingCriteriaDto> criteriaList = new ArrayList<>();
		// Populate criteriaList with necessary data

		// When
		when(underwritingCriteriaService.getAll()).thenReturn(criteriaList);

		// Then
		mockMvc.perform(get("/api/v1/underwriting/criteria/fetchAll").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
		// You can add more assertions to verify the content of the response
	}

	@Test
	void testAddCriteriaToProd() throws Exception {
		// Given
		LoanProductCriteriaDto loanProductCriteriaDto = new LoanProductCriteriaDto();
		loanProductCriteriaDto.setProductName("Mortgage Loan");
		loanProductCriteriaDto.setCriteriaNames(List.of("Credit Score Requirement", "Debt-to-Income Ratio"));

		// Populate loanProductCriteriaDto with necessary data

		// When
		doNothing().when(underwritingCriteriaService).addCriteriaToLoanProd(loanProductCriteriaDto.getCriteriaNames(),
				loanProductCriteriaDto.getProductName());

		// Then
		mockMvc.perform(
				post("/api/v1/underwriting/criteria/add-criteria-to-loanProd").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loanProductCriteriaDto)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_201))
				.andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_201));
	}

	@Test
	void testDeleteCriteriaToProd() throws Exception {
		// Given
		LoanProductCriteriaDto loanProductCriteriaDto = new LoanProductCriteriaDto();
		loanProductCriteriaDto.setProductName("ExampleProductName");
		List<String> criteriaNames = new ArrayList<>();
		criteriaNames.add("ExampleCriteriaName1");
		criteriaNames.add("ExampleCriteriaName2");
		loanProductCriteriaDto.setCriteriaNames(criteriaNames);

		// When
		doNothing().when(underwritingCriteriaService).removeCriteriaToLoanProd(criteriaNames, "ExampleProductName");

		// Then
		mockMvc.perform(delete("/api/v1/underwriting/criteria/delete-criteria-to-loanProd")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loanProductCriteriaDto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.statusCode").value(AppConstants.STATUS_200))
				.andExpect(jsonPath("$.statusMsg").value(AppConstants.MESSAGE_200));
	}

	@Test
	void testFetchByCriteriaName() throws Exception {
		// Given
		List<String> criteriaNames = new ArrayList<>();
		criteriaNames.add("Credit Score Requirement");
		criteriaNames.add("Income Verification");

		Set<Integer> loanIds = new HashSet<>();
		loanIds.add(1001);
		loanIds.add(1002);

		// When
		when(underwritingCriteriaService.getByCriterias(criteriaNames)).thenReturn(loanIds);

		// Then
		mockMvc.perform(post("/api/v1/underwriting/criteria/get-loan-by-criteria-name")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(criteriaNames)))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray());

	}

	@Test
	void testFetchAllCriteriaNames() throws Exception {
		// Given
		List<String> criteriaNames = Arrays.asList("Credit Score Requirement", "Income Verification",
				"Debt-to-Income Ratio");

		// When
		when(underwritingCriteriaService.getAllCriteriaNames()).thenReturn(criteriaNames);

		// Then
		mockMvc.perform(
				get("/api/v1/underwriting/criteria/fetchAll-criteria-names").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray());

	}

	@Test
	void testFetchByLoanId() throws Exception {
		// Given
		Integer loanId = 123;
		List<UnderwritingCriteriaDto> criteriaList = new ArrayList<>();
		// Populate criteriaList with necessary data

		// When
		when(underwritingCriteriaService.getByLoanId(loanId)).thenReturn(criteriaList);

		// Then
		mockMvc.perform(get("/api/v1/underwriting/criteria/fetch-by-loan-id/{id}", loanId)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
		// Add more assertions to verify the content of the response
	}

	 @Test
	    void addCriteriaToLoanProd_ValidInput_Returns201() throws Exception {
	        // Arrange
	        Integer productId = 123;
	        List<String> loanProductCriteriaDto = Arrays.asList("criteria1", "criteria2");

	        // Act
	        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/underwriting/criteria/add-criteria-to-loanProd/{prodId}", productId)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(loanProductCriteriaDto)))
	                .andExpect(MockMvcResultMatchers.status().isCreated())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(AppConstants.STATUS_201))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(AppConstants.MESSAGE_201));

	        // Assert
	        verify(underwritingCriteriaService).saveLoanProdCriteria(productId, loanProductCriteriaDto);
	    }

	    
}
