package com.zettamine.mpa.ucm.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.mpa.ucm.entities.UnderwritingHistory;

public interface UnderwritingHistoryRepository extends JpaRepository<UnderwritingHistory, Serializable> {

	Optional<UnderwritingHistory> findByLoanId(Integer loanId);
}
