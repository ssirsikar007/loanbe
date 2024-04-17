package com.aspire.loanbe.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.loanbe.dto.LoanRequest;
import com.aspire.loanbe.dto.RepaymentRequest;
import com.aspire.loanbe.dto.UserLoanResponse;
import com.aspire.loanbe.exception.LoanNotApprovedException;
import com.aspire.loanbe.exception.LoanNotFoundException;
import com.aspire.loanbe.message.SuccessMessageConstants;
import com.aspire.loanbe.service.ILoanService;

import jakarta.validation.Valid;

@RestController
public class LoanController {
	private final ILoanService loanService;
	private final ILoanService iloanService;

	public LoanController(ILoanService loanService, ILoanService iLoanService) {
		this.loanService = loanService;
		this.iloanService = iLoanService;
	}

	@PostMapping("/user/{id}/loan")
	public ResponseEntity<?> createLoan(@Valid @RequestBody LoanRequest loanRequest, @PathVariable Long id) {
		iloanService.createLoan(loanRequest, id);
		return new ResponseEntity<>(SuccessMessageConstants.LOAN_CREATED, HttpStatus.CREATED);

	}

	@PostMapping("/user/{id}/repay")
	public ResponseEntity<?> repay(@RequestBody RepaymentRequest repaymentRequest, @PathVariable Long id) {
		try {
			loanService.repay(repaymentRequest.getLoanId(), repaymentRequest.getAmount(), id);
			return ResponseEntity.ok("Scheduled payment repaid successfully.");
		} catch (LoanNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (LoanNotApprovedException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/user/{userId}/loans")
	public ResponseEntity<?> getLoans(@PathVariable Long userId) {
		List<UserLoanResponse> loans = (List<UserLoanResponse>) iloanService.getLoans(userId);
		return new ResponseEntity<>(loans, HttpStatus.OK);
	}

}
