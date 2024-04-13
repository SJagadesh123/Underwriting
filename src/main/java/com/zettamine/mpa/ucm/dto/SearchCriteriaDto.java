package com.zettamine.mpa.ucm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteriaDto {

	private String companyName;
	private String state;
	private String city;
	private String zipcode;
	
}
