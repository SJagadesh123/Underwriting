package com.zettamine.mpa.ucm.dto;

import com.zettamine.mpa.ucm.constants.AppConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnderwritingCriteriaDto {

	private Long criteriaId;

	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	private String criteriaName;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	private String notes;
}
