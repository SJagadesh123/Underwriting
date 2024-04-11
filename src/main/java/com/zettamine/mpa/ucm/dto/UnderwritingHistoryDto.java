package com.zettamine.mpa.ucm.dto;

import java.time.LocalDate;

import com.zettamine.mpa.ucm.constants.AppConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnderwritingHistoryDto {

	private Long historyId;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
	@Pattern(regexp = AppConstants.COMPANY_NAME_REGEX, message = AppConstants.VALID_NAME)
    private String underwritingCompanyName; 
	@NotNull(message = AppConstants.NOT_NULL)
    private Integer loanId;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
    private String decision;
	@NotNull(message = AppConstants.NOT_NULL)
	@PastOrPresent
    private LocalDate decisionDate;
	@NotBlank(message = AppConstants.PROVIDE_VALUE)
    private String notes;
}
