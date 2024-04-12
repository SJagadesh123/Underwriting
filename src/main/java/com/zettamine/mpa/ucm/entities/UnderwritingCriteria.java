package com.zettamine.mpa.ucm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
//@Table(schema = "mpa", name = "underwriting_criteria")
@Table(name = "underwriting_criteria")
public class UnderwritingCriteria extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long criteriaId;

	@Column(unique = true)
	private String criteriaName;
	
	private String notes;

}
