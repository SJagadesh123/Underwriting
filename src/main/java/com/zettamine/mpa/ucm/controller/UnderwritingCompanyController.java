/**
 * The UnderwritingCompanyController class handles HTTP requests related to underwriting companies in the underwriting module.
 */
package com.zettamine.mpa.ucm.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zettamine.mpa.ucm.constants.AppConstants;
import com.zettamine.mpa.ucm.dto.ResponseDto;
import com.zettamine.mpa.ucm.dto.SearchCriteriaDto;
import com.zettamine.mpa.ucm.dto.SearchResultDto;
import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.service.IUnderwritingCompanyService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/underwriting/company")
@AllArgsConstructor
public class UnderwritingCompanyController {

	/**
	 * Service interface for managing underwriting companies.
	 */
	private IUnderwritingCompanyService underwritingCompanyService;

	/**
	 * Creates a new underwriting company.
	 * 
	 * @param underwritingCompanyDto The company details to be saved.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> saveCompanyDetails(
			@Valid @RequestBody UnderwritingCompanyDto underwritingCompanyDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwritingCompanyService.save(underwritingCompanyDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}

	/**
	 * Updates an existing underwriting company.
	 * 
	 * @param underwritingCompanyDto The updated company details.
	 * @param uwcoId                  The ID of the company to be updated.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PutMapping("/update/{uwcoId}")
	public ResponseEntity<ResponseDto> updateCompanyDetails(
			@Valid @RequestBody UnderwritingCompanyDto underwritingCompanyDto, @PathVariable Long uwcoId)
			throws IllegalArgumentException, IllegalAccessException {

		underwritingCompanyService.update(uwcoId, underwritingCompanyDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}

	/**
	 * Fetches details of an underwriting company by ID.
	 * 
	 * @param uwcoId The ID of the company to fetch.
	 * @return ResponseEntity<UnderwritingCompanyDto> A response entity containing the fetched company details.
	 */
	@GetMapping("/fetch/{uwcoId}")
	public ResponseEntity<UnderwritingCompanyDto> getCompanyDetails(@PathVariable Long uwcoId) {
		return ResponseEntity.status(HttpStatus.OK).body(underwritingCompanyService.get(uwcoId));

	}

	/**
	 * Fetches details of all underwriting companies.
	 * 
	 * @return ResponseEntity<List<UnderwritingCompanyDto>> A response entity containing a list of all company details.
	 */
	@GetMapping("/fetchAll")
	public ResponseEntity<List<UnderwritingCompanyDto>> getAllCompanyDetails() {
		return ResponseEntity.status(HttpStatus.OK).body(underwritingCompanyService.getAll());

	}
	
	/**
	 * Fetches underwriting companies based on search criteria.
	 * 
	 * @param searchCriteriaDto The search criteria.
	 * @return ResponseEntity<Set<SearchResultDto>> A response entity containing a set of search results.
	 */
	@GetMapping("/fetch-by-criteria")
	public ResponseEntity<Set<SearchResultDto>> getBySearchCriteria(@RequestBody SearchCriteriaDto searchCriteriaDto) {
		
		return ResponseEntity.status(HttpStatus.OK).body(underwritingCompanyService.getByCriteria(searchCriteriaDto));
		
	}
}
