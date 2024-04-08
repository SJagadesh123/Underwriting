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
import com.zettamine.mpa.ucm.dto.UnderwriterDto;
import com.zettamine.mpa.ucm.service.IUnderwriterService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/underwriting/underwriter")
@AllArgsConstructor
public class UnderwriterController {
	
	private IUnderwriterService underwriterService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> saveUnderwriter(@Valid @RequestBody UnderwriterDto underwriterDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwriterService.save(underwriterDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}

	@PutMapping("/update/{underwriterId}")
	public ResponseEntity<ResponseDto> updateUnderwriter(@Valid @RequestBody UnderwriterDto underwriterDto,
			@PathVariable Long underwriterId) throws IllegalArgumentException, IllegalAccessException {

		underwriterService.update(underwriterId, underwriterDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}
	
	@GetMapping("/fetch-by-uwcId/{uwcId}")
	public ResponseEntity<List<UnderwriterDto>> fetchByUwcId(@PathVariable Long uwcId)
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwriterService.getByUwcId(uwcId));
	}
	
	@GetMapping("/fetch/{underwriterId}")
	public ResponseEntity<UnderwriterDto> fetch(@PathVariable Long underwriterId)
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwriterService.getByUnderwriterId(underwriterId));
	}
	
	@GetMapping("/fetchAll")
	public ResponseEntity<List<UnderwriterDto>> fetchAll()
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwriterService.getAll());
	}
}
