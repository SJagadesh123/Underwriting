package com.zettamine.mpa.ucm.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
//@Table(schema = "mpa" ,name = "underwriting_company")
@Table(name = "underwriting_company")
public class UnderwritingCompany extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uwco_id")
	private Long uwcoId;

	@Column(name = "name", nullable = false, length = 100, unique = true)
	private String name;

	@Column(name = "address", length = 255)
	private String address;

	@Column(name = "city", length = 100)
	private String city;

	@Column(name = "state", length = 50)
	private String state;

	@Column(name = "zipcode", length = 5)
	private String zipcode;

	@Column(name = "country", length = 100)
	private String country;

	@Column(name = "phone", length = 20, unique = true)
	private String phone;

	@Column(name = "email", length = 100, unique = true)
	private String email;

	@Column(name = "website", length = 255)
	private String website;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Column(name = "uw_claim_process", columnDefinition = "TEXT")
	private String uwClaimProcess;

	@OneToMany(mappedBy = "underwritingCompany", cascade = CascadeType.ALL)
	private List<UnderwritingHistory> underwritingHistories;

	@OneToMany(mappedBy = "underwritingCompany", cascade = CascadeType.ALL)
	private List<Underwriter> underwriters;

	@OneToMany(mappedBy = "underwritingCompany", cascade = CascadeType.ALL)
	private List<UnderwritingServiceArea> serviceAreas;

}
