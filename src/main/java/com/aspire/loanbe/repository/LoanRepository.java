package com.aspire.loanbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aspire.loanbe.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
	List<Loan> findByCustomerId(Long userId);
}
