package com.aspire.loanbe.dto;

public class RepaymentRequest {
	private long loanId;
	private double amount;

	public RepaymentRequest() {
		super();
	}

	public RepaymentRequest(long loanId, double amount) {
		super();
		this.loanId = loanId;
		this.amount = amount;
	}

	public long getLoanId() {
		return loanId;
	}

	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
