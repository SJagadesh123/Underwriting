package com.zettamine.mpa.ucm.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingCriteriaLoanProductId implements Serializable {

	private Integer prodId;
	
	private UnderwritingCriteria underwritingCriteria;
}
