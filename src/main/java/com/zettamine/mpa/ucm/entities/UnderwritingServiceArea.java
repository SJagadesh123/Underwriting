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
//@Table(schema = "mpa", name = "underwriting_service_area")
@Table(name = "underwriting_service_area")
public class UnderwritingServiceArea extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_area_id")
	private Long serviceAreaId;

	@Column(name = "county", length = 100)
	private String county;

	@Column(name = "city", length = 100)
	private String city;

	@Column(name = "state", length = 50)
	private String state;

	@Column(name = "zipcode", length = 5)
	private String zipcode;

	@ManyToOne
	@JoinColumn(name = "uwco_id")
	private UnderwritingCompany underwritingCompany;

}
