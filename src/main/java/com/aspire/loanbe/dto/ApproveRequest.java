package com.aspire.loanbe.dto;

public class ApproveRequest {
	private long loanId;

	public ApproveRequest(long loanId) {
		super();
		this.loanId = loanId;
	}

	public ApproveRequest() {
		super();
	}

	public long getLoanId() {
		return loanId;
	}

	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}

}
