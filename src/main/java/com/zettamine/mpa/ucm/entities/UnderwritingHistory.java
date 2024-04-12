package com.zettamine.mpa.ucm.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
//@Table(schema = "mpa", name = "underwriting_history")
@Table(name = "underwriting_history")
public class UnderwritingHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id")
	private Long historyId;

	@ManyToOne
	@JoinColumn(name = "uwco_id")
	private UnderwritingCompany underwritingCompany;

	@Column(name = "loan_id", unique = true)
	private Integer loanId;

	@Column(name = "decision", length = 50)
	private String decision;

	@Column(name = "decision_date")
	private LocalDate decisionDate;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;
}
