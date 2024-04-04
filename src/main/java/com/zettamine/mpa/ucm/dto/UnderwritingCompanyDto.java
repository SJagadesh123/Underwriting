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
public class UnderwritingCompanyDto {

	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.COMPANY_NAME_REGEX, message = AppConstants.VALID_NAME)
	private String name;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	private String address;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.PLACE_REGEX, message = AppConstants.VALID_NAME)
	private String city;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.PLACE_REGEX, message = AppConstants.VALID_NAME)
	private String state;

	@Pattern(regexp = AppConstants.ZIPCODE_REGEX, message = AppConstants.VALID_ZIPCODE)
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	private String zipcode;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.PLACE_REGEX, message = AppConstants.VALID_NAME)
	private String country;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.PHONE_REGEX, message = AppConstants.VALID_PHONE)
	private String phone;

	@Email(message = AppConstants.VALID_EMAIL)
	private String email;

	private String website;
	private String notes;
	private String uwClaimProcess;
}
