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
	public ResponseEntity<ResponseDto> saveHistory(@Valid @RequestBody UnderwritingHistoryDto underwritingHistoryDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwritingHistoryService.save(underwritingHistoryDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}

	@PutMapping("/update/{historyId}")
	public ResponseEntity<ResponseDto> updateHistory(@Valid @RequestBody UnderwritingHistoryDto underwritingHistoryDto,
			@PathVariable Long historyId) throws IllegalArgumentException, IllegalAccessException {

		underwritingHistoryService.update(historyId, underwritingHistoryDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}
	
	@GetMapping("/fetch-by-uwcId/{uwcId}")
	public ResponseEntity<List<UnderwritingHistoryDto>> fetchByUwcId(@PathVariable Long uwcId)
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwritingHistoryService.getByUwcId(uwcId));
	}
	
	@GetMapping("/fetch-by-loanId/{loanId}")
	public ResponseEntity<UnderwritingHistoryDto> fetchByLoanId(@PathVariable Integer loanId)
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwritingHistoryService.getByLoanId(loanId));
	}
	
	@GetMapping("/fetchAll")
	public ResponseEntity<List<UnderwritingHistoryDto>> fetchAll()
	{
		return ResponseEntity.status(HttpStatus.OK)
		.body(underwritingHistoryService.getAll());
	}
	
}
