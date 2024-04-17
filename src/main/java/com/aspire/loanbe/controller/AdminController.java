package com.aspire.loanbe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.loanbe.dto.ApproveRequest;
import com.aspire.loanbe.model.Loan;
import com.aspire.loanbe.service.ILoanService;

@RestController
public class AdminController {

	private final ILoanService loanService;

	public AdminController(ILoanService loanService) {
		this.loanService = loanService;
	}

	@PostMapping("/admin/{id}/approve")
	public ResponseEntity<?> approveLoan(@RequestBody ApproveRequest approveRequest, @PathVariable Long id) {
		Loan loan = loanService.aproveLoan(approveRequest.getLoanId(), id);
		return new ResponseEntity<>(loan, HttpStatus.OK);
	}

}
