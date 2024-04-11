package com.zettamine.mpa.ucm.dto;

import com.zettamine.mpa.ucm.constants.AppConstants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnderwriterDto {

	private Long underwriterId;
	
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.LICENCE_ID_REGEX)
    private String appraiserLicenceId;
	
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.NAME_REGEX, message = AppConstants.VALID_NAME)
    private String firstName;
	
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.NAME_REGEX,  message = AppConstants.VALID_NAME)
    private String lastName;
	
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.COMPANY_NAME_REGEX,  message = AppConstants.VALID_NAME)
    private String underwritingCompanyName;
	
	@Email(message = AppConstants.VALID_EMAIL)
    private String email;
	
	@Pattern(regexp = AppConstants.PHONE_REGEX,  message = AppConstants.VALID_PHONE)
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
    private String phone;
	
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
    private String liabilityInsurance;
}
