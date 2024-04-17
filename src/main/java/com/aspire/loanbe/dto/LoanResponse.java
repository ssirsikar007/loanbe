package com.aspire.loanbe.dto;

import java.util.Date;
import java.util.List;

import com.aspire.loanbe.model.StatusEnum;

public class LoanResponse {
	private Long id;

	private double amount;
	private int term;
	private StatusEnum state;
	private Date loanDate;
	private List<ScheduledPaymentResponse> scheduledRepayments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public StatusEnum getState() {
		return state;
	}

	public void setState(StatusEnum state) {
		this.state = state;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public List<ScheduledPaymentResponse> getScheduledRepayments() {
		return scheduledRepayments;
	}

	public void setScheduledRepayments(List<ScheduledPaymentResponse> scheduledRepayments) {
		this.scheduledRepayments = scheduledRepayments;
	}

	public LoanResponse(Long id, double amount, int term, StatusEnum statusEnum, Date loanDate,
			List<ScheduledPaymentResponse> scheduledRepayments) {
		super();
		this.id = id;
		this.amount = amount;
		this.term = term;
		this.state = statusEnum;
		this.loanDate = loanDate;
		this.scheduledRepayments = scheduledRepayments;
	}

	public LoanResponse() {
		super();
	}

}
