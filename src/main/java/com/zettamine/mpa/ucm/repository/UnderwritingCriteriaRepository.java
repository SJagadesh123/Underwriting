package com.zettamine.mpa.ucm.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.mpa.ucm.entities.UnderwritingCriteria;

public interface UnderwritingCriteriaRepository extends JpaRepository<UnderwritingCriteria, Serializable> {

	Optional<UnderwritingCriteria> findByCriteriaName(String criteriaName);


}
