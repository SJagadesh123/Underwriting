/**
 * The UnderwriterController class handles HTTP requests related to underwriters in the underwriting module.
 */
package com.zettamine.mpa.ucm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zettamine.mpa.ucm.constants.AppConstants;
import com.zettamine.mpa.ucm.dto.ResponseDto;
import com.zettamine.mpa.ucm.dto.UnderwriterDto;
import com.zettamine.mpa.ucm.service.IUnderwriterService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/underwriting/underwriter")
@AllArgsConstructor
public class UnderwriterController {
	
	/**
	 * Service interface for managing underwriters.
	 */
	private IUnderwriterService underwriterService;

	/**
	 * Creates a new underwriter.
	 * 
	 * @param underwriterDto The underwriter data to be saved.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> saveUnderwriter(@Valid @RequestBody UnderwriterDto underwriterDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwriterService.save(underwriterDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}

	/**
	 * Updates an existing underwriter.
	 * 
	 * @param underwriterDto The updated underwriter data.
	 * @param underwriterId  The ID of the underwriter to be updated.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PutMapping("/update/{underwriterId}")
	public ResponseEntity<ResponseDto> updateUnderwriter(@Valid @RequestBody UnderwriterDto underwriterDto,
			@PathVariable Long underwriterId) throws IllegalArgumentException, IllegalAccessException {

		underwriterService.update(underwriterId, underwriterDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}
	
	/**
	 * Fetches underwriters by UWC ID.
	 * 
	 * @param uwcId The UWC ID.
	 * @return ResponseEntity<List<UnderwriterDto>> A response entity containing a list of underwriters.
	 */
	@GetMapping("/fetch-by-uwcId/{uwcId}")
	public ResponseEntity<List<UnderwriterDto>> fetchByUwcId(@PathVariable Long uwcId)
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwriterService.getByUwcId(uwcId));
	}
	
	/**
	 * Fetches an underwriter by ID.
	 * 
	 * @param underwriterId The ID of the underwriter to fetch.
	 * @return ResponseEntity<UnderwriterDto> A response entity containing the fetched underwriter.
	 */
	@GetMapping("/fetch/{underwriterId}")
	public ResponseEntity<UnderwriterDto> fetch(@PathVariable Long underwriterId)
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwriterService.getByUnderwriterId(underwriterId));
	}
	
	/**
	 * Fetches all underwriters.
	 * 
	 * @return ResponseEntity<List<UnderwriterDto>> A response entity containing a list of all underwriters.
	 */
	@GetMapping("/fetchAll")
	public ResponseEntity<List<UnderwriterDto>> fetchAll()
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwriterService.getAll());
	}
}
