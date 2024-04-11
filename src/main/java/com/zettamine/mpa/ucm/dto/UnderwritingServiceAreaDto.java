package com.zettamine.mpa.ucm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingServiceAreaDto {

	private String underwritingCompanyName;
	private List<ServiceAreaDto> serviceArea;

}
