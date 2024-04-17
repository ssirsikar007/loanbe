package com.aspire.loanbe.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LoanRequest {
	@Positive(message = "Amount must be greater than 0")
	private double amount;

	@Positive(message = "Term should be greater than 0")
	private int term;

	@NotNull(message = "Date is required")
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public LoanRequest(double amount, int term, Date date) {
		super();
		this.amount = amount;
		this.term = term;
		this.date = date;
	}

}
