/**
 * The UnderwritingHistoryController class handles HTTP requests related to underwriting history in the underwriting module.
 */
package com.zettamine.mpa.ucm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zettamine.mpa.ucm.constants.AppConstants;
import com.zettamine.mpa.ucm.dto.ResponseDto;
import com.zettamine.mpa.ucm.dto.UnderwritingHistoryDto;
import com.zettamine.mpa.ucm.service.IUnderwritingHistoryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/underwriting/history")
@AllArgsConstructor
public class UnderwritingHistoryController {

	/**
	 * Service interface for managing underwriting history.
	 */
	private IUnderwritingHistoryService underwritingHistoryService;

	/**
	 * Creates a new underwriting history.
	 * 
	 * @param underwritingHistoryDto The history details to be saved.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> saveHistory(@Valid @RequestBody UnderwritingHistoryDto underwritingHistoryDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwritingHistoryService.save(underwritingHistoryDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}

	/**
	 * Updates an existing underwriting history.
	 * 
	 * @param underwritingHistoryDto The updated history details.
	 * @param historyId              The ID of the history to be updated.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PutMapping("/update/{historyId}")
	public ResponseEntity<ResponseDto> updateHistory(@Valid @RequestBody UnderwritingHistoryDto underwritingHistoryDto,
			@PathVariable Long historyId) throws IllegalArgumentException, IllegalAccessException {

		underwritingHistoryService.update(historyId, underwritingHistoryDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}
	
	/**
	 * Fetches underwriting history by underwriting company ID.
	 * 
	 * @param uwcId The ID of the underwriting company.
	 * @return ResponseEntity<List<UnderwritingHistoryDto>> A response entity containing a list of history records.
	 */
	@GetMapping("/fetch-by-uwcId/{uwcId}")
	public ResponseEntity<List<UnderwritingHistoryDto>> fetchByUwcId(@PathVariable Long uwcId)
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwritingHistoryService.getByUwcId(uwcId));
	}
	
	/**
	 * Fetches underwriting history by loan ID.
	 * 
	 * @param loanId The ID of the loan.
	 * @return ResponseEntity<UnderwritingHistoryDto> A response entity containing the history record.
	 */
	@GetMapping("/fetch-by-loanId/{loanId}")
	public ResponseEntity<UnderwritingHistoryDto> fetchByLoanId(@PathVariable Integer loanId)
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwritingHistoryService.getByLoanId(loanId));
	}
	
	/**
	 * Fetches all underwriting history records.
	 * 
	 * @return ResponseEntity<List<UnderwritingHistoryDto>> A response entity containing a list of all history records.
	 */
	@GetMapping("/fetchAll")
	public ResponseEntity<List<UnderwritingHistoryDto>> fetchAll()
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwritingHistoryService.getAll());
	}
	
}
