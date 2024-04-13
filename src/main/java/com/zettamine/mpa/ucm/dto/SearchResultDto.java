package com.zettamine.mpa.ucm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto {

	private Long companyId;
	private String name;
	private String phone;
	private String email;
	private String website;
	private String notes;
	private String uwClaimProcess;
	
}
