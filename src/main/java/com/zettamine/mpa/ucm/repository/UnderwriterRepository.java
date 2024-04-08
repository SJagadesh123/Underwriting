package com.zettamine.mpa.ucm.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.mpa.ucm.entities.Underwriter;

public interface UnderwriterRepository extends JpaRepository<Underwriter, Serializable>{

	Optional<Underwriter> findByEmail(String email);

	Optional<Underwriter> findByPhone(String phone);

	Optional<Underwriter> findByAppraiserLicenceId(String email);

	
}
