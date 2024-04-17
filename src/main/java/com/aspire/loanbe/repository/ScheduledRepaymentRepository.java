package com.aspire.loanbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aspire.loanbe.model.ScheduledRepayment;

@Repository
public interface ScheduledRepaymentRepository extends JpaRepository<ScheduledRepayment, Long> {

	List<ScheduledRepayment> getByLoanId(long loanId);

}
