package com.zettamine.mpa.ucm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

	private String statusCode;
	private String statusMsg;
}
