package com.zettamine.mpa.ucm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	private IUnderwritingHistoryService underwritingHistoryService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> saveCompanyDetails(@Valid @RequestBody UnderwritingHistoryDto underwritingHistoryDto) throws IllegalArgumentException, IllegalAccessException
	{
		
		underwritingHistoryService.save(underwritingHistoryDto);
		
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));
	
	}
}
