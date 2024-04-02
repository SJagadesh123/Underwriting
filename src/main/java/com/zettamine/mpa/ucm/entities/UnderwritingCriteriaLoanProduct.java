package com.zettamine.mpa.ucm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(schema = "mpa", name = "underwriting_criteria_loan_prod")
@Table(name = "underwriting_criteria_loan_prod")
@IdClass(UnderwritingCriteriaLoanProductId.class)
public class UnderwritingCriteriaLoanProduct extends BaseEntity {
	
	@Id
	private Integer prodId;

	@Id
	@ManyToOne
	@JoinColumn(name = "criteria_id")
	private UnderwritingCriteria underwritingCriteria;
}
