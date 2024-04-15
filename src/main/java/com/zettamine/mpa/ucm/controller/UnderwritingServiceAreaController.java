/**
 * The UnderwritingServiceAreaController class handles HTTP requests related to underwriting service areas in the underwriting module.
 */
package com.zettamine.mpa.ucm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zettamine.mpa.ucm.constants.AppConstants;
import com.zettamine.mpa.ucm.dto.ResponseDto;
import com.zettamine.mpa.ucm.dto.ServiceAreaDto;
import com.zettamine.mpa.ucm.dto.UnderwriterDto;
import com.zettamine.mpa.ucm.dto.UnderwritingServiceAreaDto;
import com.zettamine.mpa.ucm.service.IUnderwritingServiceAreaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/underwriting/service-area")
@AllArgsConstructor
public class UnderwritingServiceAreaController {

	/**
	 * Service interface for managing underwriting service areas.
	 */
	private IUnderwritingServiceAreaService serviceAreaService;

	/**
	 * Creates a new underwriting service area.
	 * 
	 * @param underwritingServiceAreaDto The service area details to be saved.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> saveUnderwriter(
			@Valid @RequestBody UnderwritingServiceAreaDto underwritingServiceAreaDto)
			throws IllegalArgumentException, IllegalAccessException {

		serviceAreaService.save(underwritingServiceAreaDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}

	/**
	 * Updates an existing underwriting service area.
	 * 
	 * @param serviceAreaDto The updated service area details.
	 * @param id             The ID of the service area to be updated.
	 * @return ResponseEntity<ResponseDto> A response entity indicating the status of the operation.
	 * @throws IllegalArgumentException When an invalid argument is provided.
	 * @throws IllegalAccessException   When illegal access occurs.
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseDto> updateUnderwriter(@Valid @RequestBody ServiceAreaDto serviceAreaDto,
			@PathVariable Long id) throws IllegalArgumentException, IllegalAccessException {

		serviceAreaService.update(id, serviceAreaDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}

	/**
	 * Fetches underwriting service area by underwriting company ID.
	 * 
	 * @param uwcId The ID of the underwriting company.
	 * @return ResponseEntity<UnderwritingServiceAreaDto> A response entity containing the service area details.
	 */
	@GetMapping("/fetch-by-uwcId/{uwcId}")
	public ResponseEntity<UnderwritingServiceAreaDto> fetchByUwcId(@PathVariable Long uwcId) {
		return ResponseEntity.status(HttpStatus.OK).body(serviceAreaService.getByUwcId(uwcId));
	}

	/**
	 * Fetches underwriting service area by ID.
	 * 
	 * @param id The ID of the service area to fetch.
	 * @return ResponseEntity<UnderwritingServiceAreaDto> A response entity containing the service area details.
	 */
	@GetMapping("/fetch/{id}")
	public ResponseEntity<UnderwritingServiceAreaDto> fetch(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(serviceAreaService.get(id));
	}

	/**
	 * Fetches all underwriting service areas.
	 * 
	 * @return ResponseEntity<List<UnderwritingServiceAreaDto>> A response entity containing a list of all service area details.
	 */
	@GetMapping("/fetchAll")
	public ResponseEntity<List<UnderwritingServiceAreaDto>> fetchAll() {
		return ResponseEntity.status(HttpStatus.OK).body(serviceAreaService.getAll());
	}
}
