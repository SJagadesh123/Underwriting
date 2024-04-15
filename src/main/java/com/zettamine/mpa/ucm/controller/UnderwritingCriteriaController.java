/**
 * The UnderwritingCriteriaController class handles HTTP requests related to underwriting criteria in the underwriting module.
 */
package com.zettamine.mpa.ucm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zettamine.mpa.ucm.constants.AppConstants;
import com.zettamine.mpa.ucm.dto.LoanProductCriteriaDto;
import com.zettamine.mpa.ucm.dto.ResponseDto;
import com.zettamine.mpa.ucm.dto.UnderwritingCriteriaDto;
import com.zettamine.mpa.ucm.service.IUnderwritingCriteriaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/underwriting/criteria")
@AllArgsConstructor
public class UnderwritingCriteriaController {
	
	/**
	 * Service interface for managing underwriting criteria.
	 */
	private IUnderwritingCriteriaService underwritingCriteriaService;

	/**
	 * Creates a new underwriting criteria.
	 * 
	 * @param underwritingCriteriaDto The criteria details to be saved.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> saveCriteria(@Valid @RequestBody UnderwritingCriteriaDto underwritingCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwritingCriteriaService.save(underwritingCriteriaDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}
	
	/**
	 * Updates an existing underwriting criteria.
	 * 
	 * @param underwritingCriteriaDto The updated criteria details.
	 * @param id                      The ID of the criteria to be updated.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseDto> updateCriteria(@Valid @RequestBody UnderwritingCriteriaDto underwritingCriteriaDto,
			@PathVariable Long id) throws IllegalArgumentException, IllegalAccessException {

		underwritingCriteriaService.update(id, underwritingCriteriaDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}
	
	/**
	 * Fetches details of an underwriting criteria by ID.
	 * 
	 * @param id The ID of the criteria to fetch.
	 * @return ResponseEntity<UnderwritingCriteriaDto> A response entity containing the fetched criteria details.
	 */
	@GetMapping("/fetch/{id}")
	public ResponseEntity<UnderwritingCriteriaDto> fetch(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(underwritingCriteriaService.get(id));
	}
	
	/**
	 * Fetches details of all underwriting criteria.
	 * 
	 * @return ResponseEntity<List<UnderwritingCriteriaDto>> A response entity containing a list of all criteria details.
	 */
	@GetMapping("/fetchAll")
	public ResponseEntity<List<UnderwritingCriteriaDto>> fetchAll() {
		return ResponseEntity.status(HttpStatus.OK).body(underwritingCriteriaService.getAll());
	}
	
	/**
	 * Adds criteria to a loan product.
	 * 
	 * @param loanProductCriteriaDto The criteria to be added to the loan product.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PostMapping("/add-criteria-to-loanProd")
	public ResponseEntity<ResponseDto> addCriteriaToProd(@Valid @RequestBody LoanProductCriteriaDto loanProductCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwritingCriteriaService.addCriteriaToLoanProd(loanProductCriteriaDto.getCriteriaNames(), loanProductCriteriaDto.getProductName());

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}
	
	@DeleteMapping("/add-criteria-to-loanProd")
	public ResponseEntity<ResponseDto> deleteCriteriaToProd(@Valid @RequestBody LoanProductCriteriaDto loanProductCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwritingCriteriaService.removeCriteriaToLoanProd(loanProductCriteriaDto.getCriteriaNames(), loanProductCriteriaDto.getProductName());

		return ResponseEntity.status(HttpStatus.OK) 
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}
}
