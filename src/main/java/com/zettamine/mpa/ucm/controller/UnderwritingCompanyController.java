package com.zettamine.mpa.ucm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zettamine.mpa.ucm.constants.AppConstants;
import com.zettamine.mpa.ucm.dto.ResponseDto;
import com.zettamine.mpa.ucm.dto.UnderwritingCompanyDto;
import com.zettamine.mpa.ucm.service.IUnderwritingCompanyService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/underwriting/company")
@AllArgsConstructor
public class UnderwritingCompanyController {
	
	private IUnderwritingCompanyService underwritingCompanyService;

	@PostMapping("/create")
	public ResponseEntity<?> saveCompanyDetails(@Valid @RequestBody UnderwritingCompanyDto underwritingCompanyDto) throws IllegalArgumentException, IllegalAccessException
	{
		
		underwritingCompanyService.save(underwritingCompanyDto);
		
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));
	
	}
	
	@PutMapping("/update/{uwcoId}")
	public ResponseEntity<?> updateCompanyDetails(@Valid @RequestBody UnderwritingCompanyDto underwritingCompanyDto, @PathVariable Long uwcoId) throws IllegalArgumentException, IllegalAccessException
	{
		
		underwritingCompanyService.update(uwcoId,underwritingCompanyDto);
		
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));
	
	}
}
