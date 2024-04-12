package com.zettamine.mpa.ucm.dto;

import java.util.List;

import com.zettamine.mpa.ucm.constants.AppConstants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingServiceAreaDto {
    
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.COMPANY_NAME_REGEX, message = AppConstants.VALID_NAME)
	private String underwritingCompanyName;
	@Valid
	private List<ServiceAreaDto> serviceArea;

}
