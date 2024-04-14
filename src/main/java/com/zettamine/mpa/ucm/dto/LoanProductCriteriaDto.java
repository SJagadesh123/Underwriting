package com.zettamine.mpa.ucm.dto;

import java.util.List;

import com.zettamine.mpa.ucm.constants.AppConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanProductCriteriaDto {

	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	private String productName;
	
	@NotNull
	private List<String> criteriaNames;
}
