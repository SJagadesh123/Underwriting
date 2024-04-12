package com.zettamine.mpa.ucm.dto;

import com.zettamine.mpa.ucm.constants.AppConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAreaDto {
 
	
	private Long serviceAreaId;
	
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	private String county;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	private String city;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	private String state;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.ZIPCODE_REGEX, message = AppConstants.VALID_ZIPCODE)
	private String zipcode;
}
