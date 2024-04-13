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
	
	private IUnderwritingCriteriaService underwritingCriteriaService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> saveCriteria(@Valid @RequestBody UnderwritingCriteriaDto underwritingCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwritingCriteriaService.save(underwritingCriteriaDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseDto> updateCriteria(@Valid @RequestBody UnderwritingCriteriaDto underwritingCriteriaDto,
			@PathVariable Long id) throws IllegalArgumentException, IllegalAccessException {

		underwritingCriteriaService.update(id, underwritingCriteriaDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200));

	}
	
	@GetMapping("/fetch/{id}")
	public ResponseEntity<UnderwritingCriteriaDto> fetch(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(underwritingCriteriaService.get(id));
	}
	
	@GetMapping("/fetchAll")
	public ResponseEntity<List<UnderwritingCriteriaDto>> fetchAll() {
		return ResponseEntity.status(HttpStatus.OK).body(underwritingCriteriaService.getAll());
	}
	
	@PostMapping("/add-criteria-to-loanProd")
	public ResponseEntity<ResponseDto> addCriteriaToProd(@Valid @RequestBody LoanProductCriteriaDto loanProductCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		underwritingCriteriaService.addCriteriaToLoanProd(loanProductCriteriaDto.getCriteriaNames(), loanProductCriteriaDto.getProductName());

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201));

	}
}
