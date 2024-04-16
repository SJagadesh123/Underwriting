package com.zettamine.mpa.ucm.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.mpa.ucm.entities.UnderwritingCriteriaLoanProduct;

public interface UnderwritingCriteriaLoanProductRepository extends JpaRepository<UnderwritingCriteriaLoanProduct, Serializable> {

	List<UnderwritingCriteriaLoanProduct> findByProdId(Integer id);

}
