package com.zettamine.mpa.ucm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	private IUnderwritingServiceAreaService serviceAreaService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> saveUnderwriter(
			@Valid @RequestBody UnderwritingServiceAreaDto underwritingServiceAreaDto)
			throws IllegalArgumentException, IllegalAccessException {

		serviceAreaService.save(underwritingServiceAreaDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseDto> updateUnderwriter(@Valid @RequestBody ServiceAreaDto serviceAreaDto,
			@PathVariable Long id) throws IllegalArgumentException, IllegalAccessException {

		serviceAreaService.update(id, serviceAreaDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}

	@GetMapping("/fetch-by-uwcId/{uwcId}")
	public ResponseEntity<UnderwritingServiceAreaDto> fetchByUwcId(@PathVariable Long uwcId) {
		return ResponseEntity.status(HttpStatus.OK).body(serviceAreaService.getByUwcId(uwcId));
	}

	@GetMapping("/fetch/{id}")
	public ResponseEntity<UnderwritingServiceAreaDto> fetch(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(serviceAreaService.get(id));
	}

	@GetMapping("/fetchAll")
	public ResponseEntity<List<UnderwritingServiceAreaDto>> fetchAll() {
		return ResponseEntity.status(HttpStatus.OK).body(serviceAreaService.getAll());
	}
}
