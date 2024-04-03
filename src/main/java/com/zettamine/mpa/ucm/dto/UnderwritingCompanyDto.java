package com.zettamine.mpa.ucm.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;

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

	@NotBlank
	private String name;
	private String address;
	private String city;
	private String state;
	
	@Pattern(regexp = AppConstants.ZIPCODE_REGEX)
	private String zipcode;
	private String country;
	private String phone;
	
	@Email
	private String email;
	
	private String website;
	private String notes;
	private String uwClaimProcess;
}
