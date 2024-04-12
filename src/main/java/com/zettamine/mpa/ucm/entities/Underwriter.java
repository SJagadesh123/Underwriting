package com.zettamine.mpa.ucm.entities;

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
//@Table(schema = "mpa", name = "underwriter")
@Table(name = "underwriter")
public class Underwriter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "underwriter_id")
	private Long underwriterId;

	@Column(name = "appraiser_licence_id", nullable = false, length = 10, unique = true)
	private String appraiserLicenceId;

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;


	@Column(name = "email", unique = true, length = 100)
	private String email;

	@Column(name = "phone", length = 20, unique = true)
	private String phone;

	@ManyToOne
	@JoinColumn(name = "uwco_id")
	private UnderwritingCompany underwritingCompany;

	@Column(name = "liability_insu", columnDefinition = "TEXT")
	private String liabilityInsurance;
}
