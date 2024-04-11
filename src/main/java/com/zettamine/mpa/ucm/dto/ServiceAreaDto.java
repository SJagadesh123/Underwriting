package com.zettamine.mpa.ucm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAreaDto {

	private Long serviceAreaId;
	private String county;
	private String city;
	private String state;
	private String zipcode;
}
