package com.zettamine.mpa.ucm.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.mpa.ucm.entities.UnderwritingCompany;

public interface UnderwritingCompanyRepository extends JpaRepository<UnderwritingCompany, Serializable> {

	Optional<UnderwritingCompany> findByName(String name);
	
	Optional<UnderwritingCompany> findByEmail(String email);
	
	Optional<UnderwritingCompany> findByPhone(String phone);
	
	
}
